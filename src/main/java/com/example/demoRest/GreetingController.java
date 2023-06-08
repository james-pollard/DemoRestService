package com.example.demoRest;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		long ctr = counter.incrementAndGet();
		System.out.println(">>>>>>>>> recieved request for name: "+ name + " assigned id: " + ctr);
		return new Greeting(ctr, String.format(template, name));
	}
}