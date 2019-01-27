package com.smartscan.db.service;

import java.util.List;

import com.smartscan.db.model.OrderItem;
import com.smartscan.db.model.OrderItemId;

public interface OrderItemService {

	public void createOrderItem(OrderItem orderITem);

	public void updateOrderItem(OrderItem orderItem);

	public void deleteOrderItem(OrderItem orderITem);

	public OrderItem findByOrderItemId(OrderItemId orderItemId);

	public List<OrderItem> findByOrderCode(String orderCode);
}
