package com.springboot.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.rest.dto.UserDto;
import com.springboot.rest.entity.User;
import com.springboot.rest.service.UserService;

@RunWith(SpringRunner.class)
public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private RequestAttributes attrubutes;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private UserService userService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		RequestContextHolder.setRequestAttributes(attrubutes);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testForCreateNewUser() throws Exception {
		UserDto dto = new UserDto();
		User user = new User();
		user.setUserId("111");
		Mockito.when(userService.addUser(dto)).thenReturn(user);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/userinfo/create")
				.accept(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void updateTestForExistingUser() throws Exception {
		UserDto dto = new UserDto();
		User user = new User();
		Mockito.when(userService.updateUser(dto, "222")).thenReturn(user);
		mockMvc.perform(MockMvcRequestBuilders
				.put("/userinfo/update/222")
				.accept(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dto))
				.contentType(MediaType.APPLICATION_JSON))
		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void getUserinfoDetails() {
		String userId = "123";
		String userName = "Test145";
		List<UserDto> userDtoList = new ArrayList<>();
		Mockito.when(userService.getAllUserInfo()).thenReturn(userDtoList);
		Assert.assertNotNull(userController.getUserinfo());
		Assert.assertNotNull(userDtoList);
		Assert.assertNotNull(userId);
		Assert.assertNotNull(userName);
	}

	@Test
	public void getUserByIdDetails() {
		String userId = "123";
		UserDto userDto = new UserDto();
		Mockito.when(userService.getUserByUserId(userId)).thenReturn(userDto);
		Assert.assertNotNull(userController.getUserById(userId));
		Assert.assertNotNull(userDto);
		Assert.assertNotNull(userId);
	}

}
