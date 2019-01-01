package com.smartscan.db.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartscan.db.model.User;
import com.smartscan.db.repository.UserRepository;
import com.smartscan.db.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public void createUser(User user) {
		repository.save(user);
	}

	@Override
	public void updateUser(User user) {
		repository.save(user);
	}

	@Override
	public void deleteUser(User user) {
		repository.delete(user);
	}

	@Override
	public User findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

}
