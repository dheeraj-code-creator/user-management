package com.springboot.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.dto.UserDto;
import com.springboot.rest.entity.User;
import com.springboot.rest.service.UserService;

@RestController
@RequestMapping(value = "/userinfo")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public User createNewUser(@RequestBody UserDto userdto) {
		return userService.addUser(userdto);
	}
	
	@PutMapping(value = "/update/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User updateExistingUser(@RequestBody UserDto userdto, @PathVariable("userId")String userId) {
		return userService.updateUser(userdto, userId);
	}

	@GetMapping(value = "/alluser", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDto> getUserinfo() {
		return userService.getAllUserInfo();
	}

	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto getUserById(@PathVariable("userId") String userId) {
		return userService.getUserByUserId(userId);
	}
	
	// delete users
	@DeleteMapping(value = "/delete/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUser(@PathVariable(value = "userId") String userId) {
		userService.deleteById(userId);
	}

}
