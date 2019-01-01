package com.smartscan.db.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartscan.db.model.UserGroup;
import com.smartscan.db.repository.UserGroupRepository;
import com.smartscan.db.service.UserGroupService;

@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private UserGroupRepository repository;

	@Override
	public void createUserGroup(UserGroup userGroup) {
		repository.save(userGroup);
	}

	@Override
	public void updateUserGroup(UserGroup userGroup) {
		repository.save(userGroup);
	}

	@Override
	public void deleteUserGroup(UserGroup userGroup) {
		repository.delete(userGroup);
	}

	@Override
	public UserGroup findByName(String groupName) {
		return repository.findByName(groupName);
	}

	@Override
	public List<UserGroup> findAll() {
		return repository.findAll();
	}

}
