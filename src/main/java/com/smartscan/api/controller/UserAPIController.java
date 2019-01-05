package com.smartscan.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smartscan.db.model.User;
import com.smartscan.db.service.UserService;
import com.smartscan.dto.UserDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class UserAPIController {
	private static final Logger logger = LoggerFactory.getLogger(UserAPIController.class);

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
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping(path = "api/users/{username}/all")
	public ResponseEntity<?> getAllUsers(@PathVariable("username") String username) {
		try {
			List<User> allUsers = userService.findAll().stream().filter(e -> !e.getUsername().equals(username))
					.collect(Collectors.toList());
			List<UserDTO> allUsersDTO = allUsers.stream().map(e -> new UserDTO(e.getUsername()))
					.collect(Collectors.toList());
			return new ResponseEntity<List<UserDTO>>(allUsersDTO, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
