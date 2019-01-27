package com.smartscan.api.controller;

import java.util.Collections;
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
import com.smartscan.db.model.Item;
import com.smartscan.db.model.Order;
import com.smartscan.db.model.OrderItem;
import com.smartscan.db.model.OrderStatus;
import com.smartscan.db.model.OrderType;
import com.smartscan.db.model.User;
import com.smartscan.db.service.CustomerService;
import com.smartscan.db.service.ItemService;
import com.smartscan.db.service.OrderItemService;
import com.smartscan.db.service.OrderService;
import com.smartscan.db.service.OrderStatusService;
import com.smartscan.db.service.OrderTypeService;
import com.smartscan.db.service.UserService;
import com.smartscan.dto.OrderDTO;
import com.smartscan.dto.OrderItemDTO;

@CrossOrigin
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

	@Autowired
	private ItemService itemService;

	@Autowired
	private OrderItemService orderItemService;

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

	@PostMapping(path = "api/orders/update")
	public ResponseEntity<?> updateOrder(@RequestBody OrderDTO orderDTO) {
		try {
			Order order = orderService.findByOrderCode(orderDTO.getCode());
			if (order == null) {
				throw new Exception("Order " + orderDTO.getCode() + " does not exist");
			}
			OrderType type = orderTypeService.findByName(orderDTO.getType());
			Customer customer = customerService.findByName(orderDTO.getCustomer());
			User checker = userService.findByUsername(orderDTO.getChecker());
			OrderStatus status = orderStatusService.findByName(orderDTO.getStatus());
			order.setType(type);
			order.setCustomer(customer);
			order.setChecker(checker);
			order.setStatus(status);
			orderService.updateOrder(order);
			return new ResponseEntity<>(order, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
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
			System.out.println("Order code " + orderCode);
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

	@PostMapping(path = "/api/orders/addItem")
	public ResponseEntity<?> addItem(@RequestBody OrderItemDTO orderItemDTO) {
		try {
			Order order = orderService.findByOrderCode(orderItemDTO.getOrderCode());
			Item item = itemService.findByBarcode(orderItemDTO.getBarcode());
			OrderItem orderItem = new OrderItem(order, item, orderItemDTO.getQuantity(), orderItemDTO.getPrice());
			if (orderItemService.findByOrderItemId(orderItem.getId()) != null) {
				throw new Exception("Duplicated order item " + orderItem);
			}
			if (orderItem.getQuantity() <= item.getInStock()) {
				item.setInStock(item.getInStock() - orderItem.getQuantity());
				orderItemService.createOrderItem(orderItem);
				itemService.updateItem(item);
			} else {
				throw new Exception("Item " + item.getBarcode() + " only has " + item.getInStock() + " in stock");
			}

			return ResponseEntity.ok(orderItem);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/api/orders/{order_code}/getItems")
	public ResponseEntity<?> getItem(@PathVariable("order_code") final String orderCode) {
		try {
			List<OrderItem> orderItems = orderItemService.findByOrderCode(orderCode);
			List<OrderItemDTO> orderItemDTOs = orderItems.stream().map(e -> {
				OrderItemDTO orderItemDTO = new OrderItemDTO(e.getId().getOrderCode(), e.getId().getBarcode(),
						e.getQuantity(), e.getPrice());
				Item item = itemService.findByBarcode(e.getId().getBarcode());
				orderItemDTO.setDescription(item.getDescription());
				orderItemDTO.setUnit(item.getUnit().getName());
				return orderItemDTO;
			}).collect(Collectors.toList());

			return new ResponseEntity<>(Collections.singletonMap("orderItems", orderItemDTOs), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("[BAD REQUEST] = " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
