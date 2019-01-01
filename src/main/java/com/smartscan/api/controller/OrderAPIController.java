package com.smartscan.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class OrderAPIController {

	@GetMapping(path = "/hello")
	public String helloWorld() {
		return "Hello world";
	}

}
