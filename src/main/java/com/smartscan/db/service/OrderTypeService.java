package com.smartscan.db.service;

import java.util.List;

import com.smartscan.db.model.OrderType;

public interface OrderTypeService {

	public void createOrderType(OrderType orderType);

	public void updateOrderType(OrderType orderType);

	public void deleteOrderType(OrderType orderType);

	public OrderType findByName(String name);

	public List<OrderType> findAll();

}
