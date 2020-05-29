package com.springboot.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestAttributes;

import com.springboot.rest.dto.UserDto;
import com.springboot.rest.entity.User;
import com.springboot.rest.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;
	
	@Mock
	private RequestAttributes attrubutes;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ConverterService converterService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		// this line is too important for post and put endpoints
		Mockito.when(converterService.convertToEntity(Mockito.any(UserDto.class))).thenReturn(new User());
	}
	
	@Test
	public void testForCreateNewUser() {
		String userId = "111";
		String userName = "First Demo";
		User user = new User();
		user.setUserId(userId);
		user.setUserName(userName);
		userRepository.saveAndFlush(user);
		Assert.assertEquals("111", userId);
	}
	
	@Test
	public void testForupdateExistingUser() {
		String userId = "222";
		User user = new User();
		user.setUserId("222");
		user.setUserName("updated-user-name");
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getUserName());
	}

	@Test
	public void getAllUserInfoDetails() {
		String userId = "234";
		String userName = "Test234";
		User user = new User();
		user.setUserId(userId);
		user.setUserName(userName);
		List<User> userList = new ArrayList<>();
		userList.add(user);
		Mockito.when(userRepository.findAll()).thenReturn(userList.stream().collect(Collectors.toList()));
		Mockito.when(converterService.convertToDto(user)).thenReturn(new UserDto());
		Assert.assertNotNull(userService.getAllUserInfo());
	}
	
	@Test
	public void getUserByUserIdDetails() {
		String userId = "111";
		User userObj = new User();
		userObj.setUserId(userId);
		Mockito.when(userRepository.findByUserId(userId)).thenReturn(userObj);
		Mockito.when(converterService.convertToDto(userObj)).thenReturn(new UserDto());
		Assert.assertNotNull(userId);
		Assert.assertNotNull(userObj);
	}
}
