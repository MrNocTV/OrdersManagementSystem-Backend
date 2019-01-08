package com.smartscan;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.smartscan.db.model.Customer;
import com.smartscan.db.model.OrderStatus;
import com.smartscan.db.model.OrderType;
import com.smartscan.db.model.Unit;
import com.smartscan.db.model.User;
import com.smartscan.db.model.UserGroup;
import com.smartscan.db.service.CustomerService;
import com.smartscan.db.service.OrderStatusService;
import com.smartscan.db.service.OrderTypeService;
import com.smartscan.db.service.UnitService;
import com.smartscan.db.service.UserGroupService;
import com.smartscan.db.service.UserService;

@SpringBootApplication
@EnableJpaRepositories
public class SmartScanApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SmartScanApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SmartScanApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private OrderStatusService orderStatusService;

	@Autowired
	private OrderTypeService orderTypeService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PasswordEncoder pwEncoder;
	
	@Autowired
	private UnitService unitService;

	@Override
	public void run(String... args) throws Exception {
		createNeccessaryData();
		logger.info("FINISH DUMP DATA");
	}

	private void createNeccessaryData() {
		String username = "Loc";
		String password = "12345678";
		password = pwEncoder.encode(password);
		UserGroup admin = userGroupService.findByName("ADMIN");
		User user = new User(username, password, new Date(), admin);
		System.out.println("Creating user " + user);
		if (userService.findByUsername(username) == null)
			userService.createUser(user);
		UserGroup checker = new UserGroup("CHECKER", "CHECKER GROUP");
		if (userGroupService.findByName("CHECKER") == null)
			userGroupService.createUserGroup(checker);
		username = "LocLoc";
		User user1 = new User(username, password, new Date(), checker);
		if (userService.findByUsername(username) == null)
			userService.createUser(user1);

		OrderStatus orderStatus = new OrderStatus("On Creation", "Test");
		if (orderStatusService.findByName(orderStatus.getName()) == null)
			orderStatusService.createOrderStatus(orderStatus);
		orderStatus = new OrderStatus("Submitted", "Test");
		if (orderStatusService.findByName(orderStatus.getName()) == null)
			orderStatusService.createOrderStatus(orderStatus);
		orderStatus = new OrderStatus("Wait for checking", "Test");
		if (orderStatusService.findByName(orderStatus.getName()) == null)
			orderStatusService.createOrderStatus(orderStatus);
		orderStatus = new OrderStatus("Being checked", "Test");
		if (orderStatusService.findByName(orderStatus.getName()) == null)
			orderStatusService.createOrderStatus(orderStatus);
		orderStatus = new OrderStatus("Complete checking", "Test");
		if (orderStatusService.findByName(orderStatus.getName()) == null)
			orderStatusService.createOrderStatus(orderStatus);
		orderStatus = new OrderStatus("Confirmed", "Test");
		if (orderStatusService.findByName(orderStatus.getName()) == null)
			orderStatusService.createOrderStatus(orderStatus);

		OrderType orderType = new OrderType("Shipping", "Test");
		if (orderTypeService.findByName(orderType.getName()) == null)
			orderTypeService.createOrderType(orderType);
		orderType = new OrderType("Retails", "Test");
		if (orderTypeService.findByName(orderType.getName()) == null)
			orderTypeService.createOrderType(orderType);

		Customer customer = new Customer("Thuong Vy 1", "Phuoc Thai", new Date());
		if (customerService.findByName(customer.getName()) == null)
			customerService.createCustomer(customer);
		customer = new Customer("Thuong Vy 2", "Phuoc Thai", new Date());
		if (customerService.findByName(customer.getName()) == null)
			customerService.createCustomer(customer);
		
		Unit unit = new Unit("Bottle", "A bottle");
		if(unitService.findByName(unit.getName()) == null)
			unitService.createUnit(unit);
		unit = new Unit("Can", "A bottle");
		if(unitService.findByName(unit.getName()) == null)
			unitService.createUnit(unit);
		
	}
}
