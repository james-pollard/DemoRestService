package com.example.demoRest;
import com.fasterxml.jackson.core.json.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import redis.clients.jedis.*;
@RestController
public class PostitController {
private long keycounter = 0;

private String jsonString = "";
private  KafkaTemplate<String, String> kafkaTemplate;
private JedisPool jedisPool;
    @Autowired
    public PostitController(KafkaTemplate<String, String> kafkaTemplate) {
        if (jedisPool == null){
            JedisPoolConfig jpc = new JedisPoolConfig();
            jedisPool = new JedisPool(jpc,"localhost",6379);
        }
        this.kafkaTemplate = kafkaTemplate;
    }

public  void sendMessage(String msg,String topic) {
    this.kafkaTemplate.send(topic, msg);
}
@CrossOrigin(origins="http://localhost:3000")
@PostMapping("/postit")
public ResponseEntity<String> processJsonFile(@RequestBody JsonData jsonData) {
    try {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HH:mm:ss").format(Calendar.getInstance().getTime());

		System.out.println(">>>>>>>>> received post" + " at: " + timeStamp);

        // Access the data from jsonData and perform any necessary processing
        String body = jsonData.getBody();
        CachedModelObject cmo = new CachedModelObject();
        cmo.setMessage(body);

       // System.out.println("subject: "+subject);
        System.out.println("body: "+ body);
        String eventobject = body + ":" + timeStamp;
        if(this.jedisPool != null) {
            try  {
                //let's see if we can cache the message:
                Jedis jedis = jedisPool.getResource();
                //list prior entries
                if (keycounter > 0){
                    System.out.println("Previous cached items:");
                    for (Integer i = 0; i <  keycounter ; i++){
                        String rkey = "key" + Integer.toString(i);
                        String val = jedis.get(rkey);
                        System.out.println(rkey + " " + val);
                    }
                }
                String cachekey = "key" + Long.toString(keycounter);
                cmo.setObjectNumber(keycounter);
                cmo.setKey(cachekey);
                cmo.setMessage(body);
                keycounter++;
                ObjectMapper om = new ObjectMapper();
                jsonString = om.writeValueAsString(cmo);
                jedis.set(cachekey, jsonString);
                jedis.expire(cachekey,150);
                System.out.println("cache succeeded");
            } catch (Exception ex) {
                System.out.println("cache attempt failed");
            }
        }
        //logic to transmit to kafka goes here...
        sendMessage(jsonString,"test-topic");
        return ResponseEntity.ok("Json data processed successfully");
    } catch (Exception e) {
        // Handle exceptions
        return ResponseEntity.status(500).body("Error processing JSON data");
    }


}
}