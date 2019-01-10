package com.smartscan.api.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartscan.db.model.Customer;
import com.smartscan.db.model.Order;
import com.smartscan.db.model.OrderStatus;
import com.smartscan.db.model.OrderType;
import com.smartscan.db.model.User;
import com.smartscan.db.service.CustomerService;
import com.smartscan.db.service.OrderService;
import com.smartscan.db.service.OrderStatusService;
import com.smartscan.db.service.OrderTypeService;
import com.smartscan.db.service.UserService;
import com.smartscan.dto.OrderDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class OrderAPIController {

	private static final Logger logger = LoggerFactory.getLogger(OrderAPIController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderTypeService orderTypeService;

	@Autowired
	private OrderStatusService orderStatusService;

	@Autowired
	private UserService userService;

	@PostMapping(path = "/api/orders")
	public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
		try {
			Order order = new Order(orderDTO.getCode(), orderDTO.getTotal(), orderDTO.getCreatedDate(),
					orderDTO.getCreatedDate(), orderDTO.getDescription());
			String customerName = orderDTO.getCustomer();
			Customer customer = customerService.findByName(customerName);
			if (customer == null) {
				throw new Exception("Customer " + customerName + " does not exist");
			}
			String statusName = orderDTO.getStatus();
			OrderStatus status = orderStatusService.findByName(statusName);
			if (status == null) {
				throw new Exception("Status " + statusName + " does not exist");
			}
			String typeName = orderDTO.getType();
			OrderType type = orderTypeService.findByName(typeName);
			if (type == null) {
				throw new Exception("Type " + typeName + " does not exist");
			}
			String ownerName = orderDTO.getOwner();
			User owner = userService.findByUsername(ownerName);
			if (owner == null) {
				throw new Exception("User (owner) " + ownerName + " does not exist");
			}
			String checkerName = orderDTO.getChecker();
			if (!checkerName.trim().equals("")) {
				User checker = userService.findByUsername(checkerName);
				if (checker != null) {
					order.setChecker(checker);
				}
			}

			order.setCustomer(customer);
			order.setStatus(status);
			order.setType(type);
			order.setOwner(owner);
			order.setTotal(0.0);

			orderService.createOrder(order);
			return new ResponseEntity<OrderDTO>(orderDTO, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/api/orders/users/{username}")
	public ResponseEntity<?> getOrdersOfUser(@PathVariable("username") String username,
			@RequestParam("filter") String filter, @RequestParam("sortOrder") String sortOrder,
			@RequestParam("pageNumber") String pageNumber, @RequestParam("pageSize") String pageSize) {
		try {
			List<Order> orderList = orderService.findByOwnerUsername(username,
					PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
			if (!filter.equals("")) {
				String lowerCase = filter.toLowerCase();
				orderList = orderList.stream().filter(order -> {
					return order.getOrderCode().toLowerCase().contains(lowerCase)
							|| order.getCustomer().getName().toLowerCase().contains(lowerCase)
							|| order.getType().getName().toLowerCase().contains(lowerCase)
							|| order.getStatus().getName().toLowerCase().contains(lowerCase);
				}).collect(Collectors.toList());
			}

			if (sortOrder.equals("") || sortOrder.equals("asc")) {
				orderList.sort(new Comparator<Order>() {
					@Override
					public int compare(Order o1, Order o2) {
						return o1.getId().compareTo(o2.getId());
					}
				});
			} else {
				orderList.sort(new Comparator<Order>() {
					@Override
					public int compare(Order o1, Order o2) {
						return o2.getId().compareTo(o1.getId());
					}
				});
			}
			System.out.println("search " + filter);
			return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/api/orders/users/{username}/next_code")
	public ResponseEntity<?> getNextCodeForOrder(@PathVariable("username") String username) {
		try {
			String nextCode = orderService.getNextOrderCode();
			return new ResponseEntity<String>(nextCode, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/api/orders/users/{username}/count")
	public ResponseEntity<?> countAllOrdersOfUser(@PathVariable("username") String username) {
		try {
			Long count = orderService.countByOwnerUsername(username);
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/api/orders/users/{username}/{order_code}")
	public ResponseEntity<?> getOrder(@PathVariable("username") String username,
			@PathVariable("order_code") String orderCode) {
		try {
			Order order = orderService.findByOrderCode(orderCode);
			if (order == null) {
				throw new Exception("Order does not exist");
			}
			return new ResponseEntity<Order>(order, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
