package com.smartscan.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.smartscan.db.model.Customer;
import com.smartscan.db.model.Order;
import com.smartscan.db.model.OrderStatus;
import com.smartscan.db.model.OrderType;
import com.smartscan.db.service.impl.CustomerServiceImpl;
import com.smartscan.db.service.impl.OrderServiceImpl;
import com.smartscan.db.service.impl.OrderStatusServiceImpl;
import com.smartscan.db.service.impl.OrderTypeServiceImpl;
import com.smartscan.utils.ModelFactory;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(value = { OrderServiceImpl.class, CustomerServiceImpl.class, OrderStatusServiceImpl.class,
		OrderTypeServiceImpl.class })
public class TestOrderService {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderStatusService orderStatusService;

	@Autowired
	private OrderTypeService orderTypeService;

	@Autowired
	private CustomerService customerService;

	@Test
	public void testCreateOrderWithNoTypeNoStatusNoCustomer() {
		Order order = ModelFactory.orderFactoryMethod(null, null, null);
		orderService.createOrder(order);
		assertEquals(1, orderService.findAll().size());
	}

	@Test
	public void testCreateOrderWithTypeAndStatusNoCustomer() {
		OrderStatus status = ModelFactory.orderStatusFactoryMethod();
		OrderType type = ModelFactory.orderTypeFactoryMethod();
		orderStatusService.createOrderStatus(status);
		orderTypeService.createOrderType(type);
		Order order = ModelFactory.orderFactoryMethod(type, status, null);
		orderService.createOrder(order);

		Order returnedOrder = orderService.findAll().get(0);
		assertEquals(status, returnedOrder.getStatus());
		assertEquals(type, returnedOrder.getType());
	}

	@Test
	public void testCreateOrderWithTypeAndStatusAndCustomer() {
		OrderStatus status = ModelFactory.orderStatusFactoryMethod();
		OrderType type = ModelFactory.orderTypeFactoryMethod();
		Customer customer = ModelFactory.customerFactoryMethod();
		orderStatusService.createOrderStatus(status);
		orderTypeService.createOrderType(type);
		customerService.createCustomer(customer);
		Order order = ModelFactory.orderFactoryMethod(type, status, customer);
		orderService.createOrder(order);

		assertEquals(1, customerService.findByName(customer.getName()).getOrders().size());
		assertTrue(customerService.findByName(customer.getName()).getOrders().contains(order));

		customer.addOrder(order);
		assertEquals(1, customer.getOrders().size());

		Order order2 = ModelFactory.orderFactoryMethod(type, status, customer);
		orderService.createOrder(order2);
		assertEquals(2, customer.getOrders().size());
		assertEquals(2, customerService.findByName(customer.getName()).getOrders().size());
		assertTrue(customer.getOrders().contains(order2));
		assertTrue(customerService.findByName(customer.getName()).getOrders().contains(order2));
		assertEquals(2, orderService.findByCustomerName(customer.getName()).size());
		assertEquals(2, orderService.findByOrderStatusName(status.getName()).size());
		assertEquals(2, orderService.findByOrderTypeName(type.getName()).size());

		orderService.deleteOrder(order2);
		assertEquals(1, customer.getOrders().size());
		assertEquals(1, customerService.findByName(customer.getName()).getOrders().size());
		assertFalse(customer.getOrders().contains(order2));
		assertFalse(customerService.findByName(customer.getName()).getOrders().contains(order2));
	}

}
