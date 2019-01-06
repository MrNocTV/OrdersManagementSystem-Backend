package com.smartscan;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.smartscan.db.model.User;
import com.smartscan.db.service.UserService;

@SpringBootApplication
@EnableJpaRepositories
public class SmartScanApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SmartScanApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder pwEncoder;

	@Override
	public void run(String... args) throws Exception {
		String username = "Loc";
		String password = "12345678";
		password = pwEncoder.encode(password);
		User user = new User(username, password, new Date(), null);
	}

}
