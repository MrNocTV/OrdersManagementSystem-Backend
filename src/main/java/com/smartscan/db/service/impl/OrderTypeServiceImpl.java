package com.smartscan.db.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartscan.db.model.OrderType;
import com.smartscan.db.repository.OrderTypeRepository;
import com.smartscan.db.service.OrderTypeService;

@Service
@Transactional
public class OrderTypeServiceImpl implements OrderTypeService {

	@Autowired
	private OrderTypeRepository orderTypeRepository;

	@Override
	public void createOrderType(OrderType orderType) {
		orderTypeRepository.save(orderType);
	}

	@Override
	public void updateOrderType(OrderType orderType) {
		orderTypeRepository.save(orderType);
	}

	@Override
	public void deleteOrderType(OrderType orderType) {
		orderTypeRepository.delete(orderType);
	}

	@Override
	public OrderType findByName(String name) {
		return orderTypeRepository.findByName(name);
	}

	@Override
	public List<OrderType> findAll() {
		return orderTypeRepository.findAll();
	}

}
