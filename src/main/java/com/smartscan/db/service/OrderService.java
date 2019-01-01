package com.smartscan.db.service;

import java.util.List;

import com.smartscan.db.model.Order;

public interface OrderService {

	public void createOrder(Order order);

	public void updateOrder(Order order);

	public void deleteOrder(Order order);

	public Order findByOrderCode(String code);

	public List<Order> findByCustomerName(String name);

	public List<Order> findByOrderTypeName(String name);

	public List<Order> findByOrderStatusName(String name);

	public List<Order> findByOwnerUsername(String username);

	public List<Order> findAll();
}
