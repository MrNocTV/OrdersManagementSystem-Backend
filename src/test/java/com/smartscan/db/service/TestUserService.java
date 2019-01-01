package com.smartscan.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.smartscan.db.model.User;
import com.smartscan.db.service.impl.UserServiceImpl;
import com.smartscan.utils.ModelFactory;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(value = { UserServiceImpl.class })
public class TestUserService {

	@Autowired
	private UserService userService;

	@Before
	public void setUp() {
	}

	@Test
	public void testCreateUser() {
		User user = ModelFactory.userFactoryMethod(null);
		userService.createUser(user);
		assertEquals(1, userService.findAll().size());
	}

	@Test
	public void testUpdateUser() {
		User user = ModelFactory.userFactoryMethod(null);
		String username = user.getUsername();
		userService.createUser(user);
		user.setUsername("haha");
		userService.updateUser(user);
		assertNull(userService.findByUsername(username));
		assertNotNull(userService.findByUsername("haha"));
	}

	@Test
	public void testDeleteUser() {
		User user = ModelFactory.userFactoryMethod(null);
		userService.createUser(user);
		assertEquals(1, userService.findAll().size());
		userService.deleteUser(user);
		assertNull(userService.findByUsername(user.getUsername()));
	}

	@Test
	public void testFindByUsername() {
		User user = ModelFactory.userFactoryMethod(null);
		userService.createUser(user);
		assertNotNull(userService.findByUsername(user.getUsername()));
	}

}
