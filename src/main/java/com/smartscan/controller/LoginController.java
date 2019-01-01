package com.smartscan.controller;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smartscan.db.model.User;
import com.smartscan.db.service.UserService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class LoginController {

	@Autowired
	private UserService userService;

	@SuppressWarnings("unchecked")
	@GetMapping("/basicauth/{token}")
	public Map<String, String> basicAuth(@PathVariable String token) {
		token = token.replace("Basic", "").trim();
		String decodedToken = new String(Base64.getDecoder().decode(token));
		decodedToken = decodedToken.trim();
		if (!decodedToken.contains(":")) {
			return Collections.singletonMap("ERROR", "INVALID TOKEN");
		}
		String[] tokens = decodedToken.split(":");
		String username = tokens[0];
		String password = tokens[1];
		User user = userService.findByUsername(username);
		boolean isPasswordCorrect = BCrypt.checkpw(password, user.getPassword());
		if (isPasswordCorrect)
			return Collections.singletonMap("TOKEN", token);
		return Collections.EMPTY_MAP;
	}
}
