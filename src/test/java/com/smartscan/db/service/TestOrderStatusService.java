package com.smartscan.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.smartscan.db.model.OrderStatus;
import com.smartscan.db.service.impl.OrderStatusServiceImpl;
import com.smartscan.utils.ModelFactory;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(value = { OrderStatusServiceImpl.class })
public class TestOrderStatusService {

	@Autowired
	private OrderStatusService orderStatusService;

	@Test
	public void testCreateOrderStatus() {
		OrderStatus orderStatus = ModelFactory.orderStatusFactoryMethod();
		orderStatusService.createOrderStatus(orderStatus);
		assertEquals(1, orderStatusService.findAll().size());
	}

	@Test
	public void testUpdateOrderStatus() {
		OrderStatus orderStatus = ModelFactory.orderStatusFactoryMethod();
		orderStatusService.createOrderStatus(orderStatus);
		assertEquals(1, orderStatusService.findAll().size());
		orderStatus.setName("TESTNAME");
		assertNotNull(orderStatusService.findByName("TESTNAME"));
		assertEquals(1, orderStatusService.findAll().size());
	}

	@Test
	public void testDeleteOrderStatus() {
		OrderStatus orderStatus = ModelFactory.orderStatusFactoryMethod();
		orderStatusService.createOrderStatus(orderStatus);
		assertEquals(1, orderStatusService.findAll().size());
		orderStatusService.deleteOrderStatus(orderStatus);
		assertEquals(0, orderStatusService.findAll().size());
	}

	@Test
	public void testFindByName() {
		OrderStatus orderStatus = ModelFactory.orderStatusFactoryMethod();
		orderStatusService.createOrderStatus(orderStatus);
		assertNotNull(orderStatusService.findByName(orderStatus.getName()));
	}

}
