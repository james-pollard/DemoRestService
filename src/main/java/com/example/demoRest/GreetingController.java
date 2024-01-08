package com.example.demoRest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	@CrossOrigin(origins="http://localhost:3000")
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HH:mm:ss").format(Calendar.getInstance().getTime());
		System.out.println(">>>>>>>>> recieved request for name: "+ name + " at: " + timeStamp);
		Greeting g = new Greeting(counter.incrementAndGet(), String.format(template, name));
		System.out.println(">>>>>>>>>>>>>returning: " + g.getContent());
		return g;
	}
	
//	@PostMapping("/postit")
//	public ResponseEntity<String> processJsonFile(@RequestBody JsonData jsonData) {
//        try {
//    		System.out.println(">>>>>>>>> recieved post");
//
//            // Access the data from jsonData and perform any necessary processing
//            String subject = jsonData.getSubject();
//            String body = jsonData.getBody();
//
//            // Add your business logic here
//
//            return ResponseEntity.ok("Json data processed successfully");
//        } catch (Exception e) {
//            // Handle exceptions
//            return ResponseEntity.status(500).body("Error processing JSON data");
//        }
//    }
	
}