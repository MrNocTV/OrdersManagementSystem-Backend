package com.smartscan.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartscan.db.model.Customer;
import com.smartscan.db.service.CustomerService;
import com.smartscan.dto.CustomerDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class CustomerAPIController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerAPIController.class);

	@Autowired
	private CustomerService customerService;

	@PostMapping(path = "/api/customer")
	public ResponseEntity<?> addCustomer(@RequestBody CustomerDTO customerDTO) {
		try {
			Customer customer = new Customer(customerDTO.getName(), customerDTO.getAddress(),
					customerDTO.getCreatedDate());
			customerService.createCustomer(customer);
			return new ResponseEntity<Customer>(customer, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/api/customer")
	public ResponseEntity<?> getAllCustomers() {
		try {
			List<Customer> allCustomers = customerService.findAll();
			return new ResponseEntity<List<Customer>>(allCustomers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
