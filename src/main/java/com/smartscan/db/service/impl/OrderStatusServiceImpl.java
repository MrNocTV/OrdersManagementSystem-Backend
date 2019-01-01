package com.smartscan.db.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartscan.db.model.OrderStatus;
import com.smartscan.db.repository.OrderStatusRepository;
import com.smartscan.db.service.OrderStatusService;

@Service
@Transactional
public class OrderStatusServiceImpl implements OrderStatusService {

	@Autowired
	private OrderStatusRepository orderStatusRepository;

	@Override
	public void createOrderStatus(OrderStatus status) {
		orderStatusRepository.save(status);
	}

	@Override
	public void updateOrderStatus(OrderStatus status) {
		orderStatusRepository.save(status);
	}

	@Override
	public void deleteOrderStatus(OrderStatus status) {
		orderStatusRepository.delete(status);
	}

	@Override
	public OrderStatus findByName(String name) {
		return orderStatusRepository.findByName(name);
	}

	@Override
	public List<OrderStatus> findAll() {
		return orderStatusRepository.findAll();
	}

}
