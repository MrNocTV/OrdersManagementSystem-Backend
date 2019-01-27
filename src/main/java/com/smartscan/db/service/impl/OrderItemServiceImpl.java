package com.smartscan.db.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartscan.db.model.OrderItem;
import com.smartscan.db.model.OrderItemId;
import com.smartscan.db.repository.OrderItemRepository;
import com.smartscan.db.service.OrderItemService;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public void createOrderItem(OrderItem orderItem) {
		orderItemRepository.save(orderItem);
	}

	@Override
	public void updateOrderItem(OrderItem orderItem) {
		orderItemRepository.save(orderItem);
	}

	@Override
	public void deleteOrderItem(OrderItem orderITem) {
		orderItemRepository.delete(orderITem);
	}

	@Override
	public OrderItem findByOrderItemId(OrderItemId orderItemId) {
		return orderItemRepository.findById(orderItemId).orElse(null);
	}

	@Override
	public List<OrderItem> findByOrderCode(String orderCode) {
		return orderItemRepository.findById_OrderCode(orderCode);
	}

}
