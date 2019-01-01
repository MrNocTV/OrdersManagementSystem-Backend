package com.smartscan.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.smartscan.db.model.UserGroup;
import com.smartscan.db.service.impl.UserGroupServiceImpl;
import com.smartscan.utils.ModelFactory;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(value = { UserGroupServiceImpl.class })
public class TestUserGroupService {

	@Autowired
	private UserGroupService userGroupService;

	@Test
	public void testCreateUserGroup() {
		UserGroup userGroup = ModelFactory.userGroupFactoryMethod();
		userGroupService.createUserGroup(userGroup);
		assertEquals(1, userGroupService.findAll().size());
	}

	@Test
	public void testUpdateUserGroup() {
		UserGroup userGroup = ModelFactory.userGroupFactoryMethod();
		userGroupService.createUserGroup(userGroup);
		assertEquals(1, userGroupService.findAll().size());
		userGroup.setName("TEST");
		userGroupService.updateUserGroup(userGroup);
		assertEquals("TEST", userGroupService.findAll().get(0).getName());
	}

	@Test
	public void testDeleteUserGroup() {
		UserGroup userGroup = ModelFactory.userGroupFactoryMethod();
		userGroupService.createUserGroup(userGroup);
		assertEquals(1, userGroupService.findAll().size());
		userGroupService.deleteUserGroup(userGroup);
		assertEquals(0, userGroupService.findAll().size());
	}

	@Test
	public void testFindByName() {
		UserGroup userGroup = ModelFactory.userGroupFactoryMethod();
		userGroupService.createUserGroup(userGroup);
		assertNotNull(userGroupService.findByName(userGroup.getName()));
	}

}
