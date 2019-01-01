package com.smartscan;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SmartScanApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SmartScanApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
