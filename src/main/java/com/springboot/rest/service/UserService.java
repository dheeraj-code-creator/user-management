package com.springboot.rest.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.rest.dto.UserDto;
import com.springboot.rest.entity.User;
import com.springboot.rest.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ConverterService converterService;
	
	public User addUser(UserDto userdto) {
		User convertedUser = converterService.convertToEntity(userdto);
		convertedUser.setUserId(userdto.getUserId());
		convertedUser.setUserName(userdto.getUserName());
	    return userRepository.save(convertedUser);
	}
	
	public User updateUser(UserDto userdto, String userId) {
		User userUpdate = converterService.convertToEntity(userdto);
		userUpdate = userRepository.findByUserId(userId);
		userUpdate.setUserName(userdto.getUserName());
		return userRepository.save(userUpdate);
	}

	public List<UserDto> getAllUserInfo() {
		List<User> userList = Arrays.asList(
				new User("111", "First Demo"),
				new User("222", "Second Demo"));
		userRepository.saveAll(userList);
		List<User> userDataList = userRepository.findAll();
		return userDataList.stream().map(converterService::convertToDto).collect(Collectors.toList());
	}

	
	  public UserDto getUserByUserId(String userId) { 
		  User userObj = userRepository.findById(userId).orElse(null);
		  return converterService.convertToDto(userObj);
	  }

	public void deleteById(String userId) {
		userRepository.deleteById(userId);
	}
}
