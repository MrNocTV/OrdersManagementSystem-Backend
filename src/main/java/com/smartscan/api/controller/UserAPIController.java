package com.smartscan.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smartscan.db.model.User;
import com.smartscan.db.service.UserService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class UserAPIController {

	@Autowired
	private UserService userService;

	@GetMapping(path = "api/users/{username}")
	public ResponseEntity<?> getUser(@PathVariable("username") String username) {
		try {
			User user = userService.findByUsername(username);
			if (user == null)
				throw new Exception("User does not exist");
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
}
