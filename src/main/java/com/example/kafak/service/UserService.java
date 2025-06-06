package com.example.kafak.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.kafak.documets.UserDocument;
import com.example.kafak.repo.UserRepository;
import com.example.kafak.requestbody.LoginDetails;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDocument createUser(UserDocument user) {
		return userRepository.save(user);
	}

	public UserDocument getUserById(String id) {
		return userRepository.findById(id).orElse(null);
	}

	public UserDocument checkLogin(LoginDetails loginDetails) {

		return switch (loginDetails.getType()) {
		case "email" ->
			userRepository.findByEmailAndPasswordHash(loginDetails.getUserName(), loginDetails.getPassword());
		case "phone" ->
			userRepository.findByPhoneAndPasswordHash(loginDetails.getUserName(), loginDetails.getPassword());
		default -> userRepository.findByUsernameAndPasswordHash(loginDetails.getUserName(), loginDetails.getPassword());
		};
	}

	public List<UserDocument> getAllUserNames() {
		List<UserDocument> findAll = userRepository.findAll();
		return findAll;
	}

	public List<UserDocument> searchByMatchUsername(String search) {
		return userRepository.findByUsernameContainingIgnoreCase(search);
	 
	}

	public UserDocument findByUserName(String user2) {
		
		return userRepository.findById(user2).orElse(null);
		
	}
}
