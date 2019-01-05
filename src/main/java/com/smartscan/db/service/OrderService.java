package com.smartscan.db.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.smartscan.db.model.Order;

public interface OrderService {

	public void createOrder(Order order);

	public void updateOrder(Order order);

	public void deleteOrder(Order order);

	public Order findByOrderCode(String code);

	public List<Order> findByCustomerName(String name);

	public List<Order> findByOrderTypeName(String name);

	public List<Order> findByOrderStatusName(String name);

	public List<Order> findByOwnerUsername(String username, Pageable pageable);
	
	public Long countByOwnerUsername(String username);

	public List<Order> findByCheckerUsername(String username);

	public List<Order> findAll();

	public String getNextOrderCode();
	
}
