package com.smartscan.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.smartscan.db.model.Customer;
import com.smartscan.db.service.impl.CustomerServiceImpl;
import com.smartscan.utils.ModelFactory;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(value = { CustomerServiceImpl.class })
public class TestCustomerService {

	@Autowired
	private CustomerService customerService;

	@Test
	public void testCreateCustomer() {
		Customer customer = ModelFactory.customerFactoryMethod();
		customerService.createCustomer(customer);
		assertEquals(1, customerService.findAll().size());
	}

	@Test
	public void testUpdateCustomer() {
		Customer customer = ModelFactory.customerFactoryMethod();
		customerService.createCustomer(customer);
		assertEquals(1, customerService.findAll().size());
		customer.setName("TESTNAME");
		customerService.updateCustomer(customer);
		assertNotNull(customerService.findByName("TESTNAME"));
	}

	@Test
	public void testDelelteCustomer() {
		Customer customer = ModelFactory.customerFactoryMethod();
		customerService.createCustomer(customer);
		assertEquals(1, customerService.findAll().size());
		customerService.deleteCustomer(customer);
		assertEquals(0, customerService.findAll().size());
	}

	@Test
	public void testFindByName() {
		Customer customer = ModelFactory.customerFactoryMethod();
		customerService.createCustomer(customer);
		assertEquals(1, customerService.findAll().size());
		assertNotNull(customerService.findByName(customer.getName()));
	}

}
