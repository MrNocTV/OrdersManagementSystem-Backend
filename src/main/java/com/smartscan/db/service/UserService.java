package com.smartscan.db.service;

import java.util.List;

import com.smartscan.db.model.User;

public interface UserService {

	public void createUser(User user);

	public void updateUser(User user);

	public void deleteUser(User user);

	public User findByUsername(String username);

	public List<User> findAll();
}
