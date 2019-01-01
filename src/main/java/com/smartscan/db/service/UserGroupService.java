package com.smartscan.db.service;

import java.util.List;

import com.smartscan.db.model.UserGroup;

public interface UserGroupService {

	public void createUserGroup(UserGroup userGroup);

	public void updateUserGroup(UserGroup userGroup);

	public void deleteUserGroup(UserGroup userGroup);

	public UserGroup findByName(String groupName);

	public List<UserGroup> findAll();

}
