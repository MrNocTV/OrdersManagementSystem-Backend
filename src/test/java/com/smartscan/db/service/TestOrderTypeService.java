package com.smartscan.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.smartscan.db.model.OrderType;
import com.smartscan.db.service.impl.OrderTypeServiceImpl;
import com.smartscan.utils.ModelFactory;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(value = { OrderTypeServiceImpl.class })
public class TestOrderTypeService {

	@Autowired
	private OrderTypeService orderTypeService;

	@Test
	public void testCreateOrderStatus() {
		OrderType orderType = ModelFactory.orderTypeFactoryMethod();
		orderTypeService.createOrderType(orderType);
		assertEquals(1, orderTypeService.findAll().size());
	}

	@Test
	public void testUpdateOrderStatus() {
		OrderType orderType = ModelFactory.orderTypeFactoryMethod();
		orderTypeService.createOrderType(orderType);
		assertEquals(1, orderTypeService.findAll().size());
		orderType.setName("TESTNAME");
		assertNotNull(orderTypeService.findByName("TESTNAME"));
		assertEquals(1, orderTypeService.findAll().size());
	}

	@Test
	public void testDeleteOrderStatus() {
		OrderType orderType = ModelFactory.orderTypeFactoryMethod();
		orderTypeService.createOrderType(orderType);
		assertEquals(1, orderTypeService.findAll().size());
		orderTypeService.deleteOrderType(orderType);
		assertEquals(0, orderTypeService.findAll().size());
	}

	@Test
	public void testFindByName() {
		OrderType orderType = ModelFactory.orderTypeFactoryMethod();
		orderTypeService.createOrderType(orderType);
		assertNotNull(orderTypeService.findByName(orderType.getName()));
	}
}
