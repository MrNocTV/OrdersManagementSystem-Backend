package com.smartscan.db.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartscan.db.model.Customer;
import com.smartscan.db.repository.CustomerRepository;
import com.smartscan.db.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public void createCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
	}

	@Override
	public Customer findByName(String name) {
		return customerRepository.findByName(name);
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

}
