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

import com.smartscan.db.model.OrderType;
import com.smartscan.db.service.OrderTypeService;
import com.smartscan.dto.OrderTypeDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class OrderTypeAPIController {

	private static final Logger logger = LoggerFactory.getLogger(OrderTypeAPIController.class);

	@Autowired
	private OrderTypeService orderTypeService;

	@PostMapping(path = "/api/order_type")
	public ResponseEntity<?> addOrderType(@RequestBody OrderTypeDTO orderTypeDTO) {
		try {
			OrderType orderType = new OrderType(orderTypeDTO.getName(), orderTypeDTO.getDescription());
			orderTypeService.createOrderType(orderType);
			return new ResponseEntity<OrderType>(orderType, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/api/order_type")
	public ResponseEntity<?> getAllOrderTypes() {
		try {
			List<OrderType> allOrderTypes = orderTypeService.findAll();
			return new ResponseEntity<List<OrderType>>(allOrderTypes, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
