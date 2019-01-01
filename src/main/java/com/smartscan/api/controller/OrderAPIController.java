package com.smartscan.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartscan.db.model.Order;
import com.smartscan.dto.OrderDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class OrderAPIController {

	@PostMapping(path = "/api/orders")
	public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
		try {
			Order order = new Order(orderDTO.getCode(), orderDTO.getTotal(), orderDTO.getCreatedDate(),
					orderDTO.getCreatedDate(), orderDTO.getDescription());
			System.out.println("ORDER " + order);
			System.out.println("ORDER DTO " + orderDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("BAD", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

}
