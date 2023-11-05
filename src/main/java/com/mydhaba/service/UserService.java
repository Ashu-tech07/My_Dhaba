package com.mydhaba.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydhaba.CustomResponse;
import com.mydhaba.model.User;
import com.mydhaba.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public CustomResponse getUserByUsernameAndPassword(String username, String password) {	
	try {
		Optional<User> optionalUser =userRepository.findByUsernameAndPassword(username, password);
		
		if(optionalUser.isPresent()) {
			return new CustomResponse("Login successfully!!", optionalUser.get());
		}
		return new CustomResponse("Invalid credentials",null);
		
	} catch (Exception e) {
		return new CustomResponse("Somethings went wrong!!",null);
	}
	}
	
	public User getUserById(int id) {
		Optional<User> optionalUser=userRepository.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}
		return null;
	}
	
	public CustomResponse getAllUsers() {
		List<User> users=userRepository.findAll();
		if(users.size()!=0) {
			return new CustomResponse("success",users);
		}else {
			return new CustomResponse(" NO User found!!",null);
		}
	}
	
	public CustomResponse addUser(User user) {
		try {
			Optional<User> optionalUser=userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
			if(optionalUser.isPresent()) {
				return new CustomResponse("User already exist with same name and password", null);
			}
			else {
				User newUser=userRepository.save(user);
				return new CustomResponse("Registration successful",newUser);
			}
		} catch (Exception e) {
			return new CustomResponse("Somethings went wrong!!",null);
		}
	}
	
	public User updateUser(int id, User user) {
		Optional<User> optionalUser=userRepository.findById(id);
		if(optionalUser.isPresent()) {
			User newUser=optionalUser.get();
			newUser.setUsername(user.getUsername());
			newUser.setEmail(user.getEmail());
			newUser.setDob(user.getDob());
			newUser.setRole(user.getRole());
			
			return userRepository.save(newUser);
		}
		return null;
	}
	
	public boolean deleteUser(User user) {
		try {
			userRepository.delete(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
