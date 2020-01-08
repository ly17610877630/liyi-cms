package com.liyi.cms.tests;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liyi.cms.dao.UserDao;
import com.liyi.cms.pojo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class UserTest {
	
	@Autowired
	public UserDao userDao;
	
	@Test
	public void select() {
		List<User> list = userDao.select(new User());
		System.out.println(list);
	}
	
	@Test
	public void selectById() {
		User user = userDao.selectById(199);
		System.out.println(user);
	}
	
	@Test
	public void insert() {
		User user = new User();
		user.setNickname("盼盼");
		int i = userDao.insert(user);
		if(i>0) {
			List<User> list = userDao.select(null);
			System.out.println(list);
		}
	}
	
	@Test
	public void update() {
		User user = new User();
		user.setId(199);
		user.setNickname("盼盼");
		int i = userDao.update(user);
		if(i>0) {
			List<User> list = userDao.select(null);
			System.out.println(list);
		}
	}
	
	@Test
	public void delete() {
		int i = userDao.delete("201");
		if(i>0) {
			List<User> list = userDao.select(null);
			System.out.println(list);
		}
	}
}
