package com.smartscan.db.service;

import java.util.List;

import com.smartscan.db.model.Customer;

public interface CustomerService {

	public void createCustomer(Customer customer);

	public void updateCustomer(Customer customer);

	public void deleteCustomer(Customer customer);

	public Customer findByName(String name);

	public List<Customer> findAll();
}
