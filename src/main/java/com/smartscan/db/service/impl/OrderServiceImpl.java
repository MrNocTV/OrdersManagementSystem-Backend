package com.smartscan.db.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartscan.db.model.Order;
import com.smartscan.db.repository.OrderRepository;
import com.smartscan.db.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public void createOrder(Order order) {
		orderRepository.save(order);
	}

	@Override
	public void updateOrder(Order order) {
		orderRepository.save(order);
	}

	@Override
	public void deleteOrder(Order order) {
		order.getCustomer().removeOrder(order);
		orderRepository.delete(order);
	}

	@Override
	public Order findByOrderCode(String code) {
		return orderRepository.findByOrderCode(code);
	}

	@Override
	public List<Order> findByCustomerName(String name) {
		return orderRepository.findByCustomer_Name(name);
	}

	@Override
	public List<Order> findByOrderTypeName(String name) {
		return orderRepository.findByType_Name(name);
	}

	@Override
	public List<Order> findByOrderStatusName(String name) {
		return orderRepository.findByStatus_Name(name);
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> findByOwnerUsername(String username, Pageable pageable) {
		return orderRepository.findByOwner_Username(username, pageable);
	}

	@Override
	public List<Order> findByCheckerUsername(String username) {
		return orderRepository.findByChecker_Username(username);
	}

	@Override
	public String getNextOrderCode() {
		return String.valueOf(1000l + (orderRepository.count() + 1));
	}

	@Override
	public Long countByOwnerUsername(String username) {
		return orderRepository.countByOwner_Username(username);
	}

}
