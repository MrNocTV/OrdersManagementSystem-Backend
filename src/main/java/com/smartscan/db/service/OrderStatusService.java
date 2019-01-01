package com.smartscan.db.service;

import java.util.List;

import com.smartscan.db.model.OrderStatus;

public interface OrderStatusService {

	public void createOrderStatus(OrderStatus status);

	public void updateOrderStatus(OrderStatus status);

	public void deleteOrderStatus(OrderStatus status);

	public OrderStatus findByName(String name);

	public List<OrderStatus> findAll();

}
