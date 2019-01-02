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

import com.smartscan.db.model.OrderStatus;
import com.smartscan.db.service.OrderStatusService;
import com.smartscan.dto.OrderStatusDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class OrderStatusAPIController {

	private static final Logger logger = LoggerFactory.getLogger(OrderStatusAPIController.class);

	@Autowired
	private OrderStatusService orderStatusService;

	@PostMapping(path = "/api/order_status")
	public ResponseEntity<?> addOrderStatus(@RequestBody OrderStatusDTO orderStatusDTO) {
		try {
			OrderStatus orderStatus = new OrderStatus(orderStatusDTO.getName(), orderStatusDTO.getDescription());
			orderStatusService.createOrderStatus(orderStatus);
			return new ResponseEntity<OrderStatus>(orderStatus, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/api/order_status")
	public ResponseEntity<?> getAllOrderStatuses() {
		try {
			List<OrderStatus> allOrderStatuses = orderStatusService.findAll();
			return new ResponseEntity<List<OrderStatus>>(allOrderStatuses, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
