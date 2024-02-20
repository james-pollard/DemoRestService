package com.example.demoRest;



import java.text.SimpleDateFormat;
import java.util.Calendar;

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

@RestController
public class PostitController {

private  KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public PostitController(KafkaTemplate<String, String> kafkaTemplate) {
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
       // String subject = jsonData.getSubject();
        String body = jsonData.getBody();
       // System.out.println("subject: "+subject);
        System.out.println("body: "+ body);
        //logic to transmit to kafka goes here...
        sendMessage(body,"test-topic");
        return ResponseEntity.ok("Json data processed successfully");
    } catch (Exception e) {
        // Handle exceptions
        return ResponseEntity.status(500).body("Error processing JSON data");
    }


}
}