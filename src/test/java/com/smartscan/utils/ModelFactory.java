package com.smartscan.utils;

import java.util.Date;
import java.util.Random;

import org.fluttercode.datafactory.impl.DataFactory;

import com.smartscan.db.model.Customer;
import com.smartscan.db.model.Order;
import com.smartscan.db.model.OrderStatus;
import com.smartscan.db.model.OrderType;
import com.smartscan.db.model.User;
import com.smartscan.db.model.UserGroup;

@SuppressWarnings("deprecation")
public class ModelFactory {
	private static final DataFactory df = new DataFactory();
	private static final Random random = new Random();

	public static User userFactoryMethod(UserGroup userGroup) {
		User user = new User(df.getName(), df.getRandomChars(20), df.getDateBetween(new Date(1996, 1, 1), new Date()),
				userGroup);
		return user;
	}

	public static UserGroup userGroupFactoryMethod() {
		UserGroup userGroup = new UserGroup(df.getName(), df.getRandomChars(20));
		return userGroup;
	}

	public static Customer customerFactoryMethod() {
		Customer customer = new Customer(df.getName(), df.getAddress(),
				df.getDateBetween(new Date(1996, 1, 1), new Date()));
		return customer;
	}

	public static OrderStatus orderStatusFactoryMethod() {
		OrderStatus orderStatus = new OrderStatus(df.getName(), df.getRandomChars(10, 29));
		return orderStatus;
	}

	public static OrderType orderTypeFactoryMethod() {
		OrderType orderType = new OrderType(df.getName(), df.getRandomChars(10, 20));
		return orderType;
	}

	public static Order orderFactoryMethod(OrderType type, OrderStatus status, Customer customer) {
		Order order = new Order(df.getRandomChars(6, 10), random.nextDouble(), df.getBirthDate(), df.getBirthDate(),
				df.getRandomChars(6, 20));
		order.setStatus(status);
		order.setType(type);
		if (customer != null)
			customer.addOrder(order);
		return order;
	}
}
