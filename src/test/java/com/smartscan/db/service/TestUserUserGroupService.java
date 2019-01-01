package com.smartscan.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.smartscan.db.model.User;
import com.smartscan.db.model.UserGroup;
import com.smartscan.db.service.impl.UserGroupServiceImpl;
import com.smartscan.db.service.impl.UserServiceImpl;
import com.smartscan.utils.ModelFactory;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(value = { UserServiceImpl.class, UserGroupServiceImpl.class })
public class TestUserUserGroupService {

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupService userGroupService;

	@Test
	public void testCreateUserAndUserGroup() {
		UserGroup userGroup = ModelFactory.userGroupFactoryMethod();
		userGroupService.createUserGroup(userGroup);
		User user = ModelFactory.userFactoryMethod(userGroup);
		userService.createUser(user);
		User returnedUser = userService.findByUsername(user.getUsername());
		assertNotNull(returnedUser.getUserGroup());
		assertEquals(userGroup, returnedUser.getUserGroup());
	}
}
