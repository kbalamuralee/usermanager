package com.test.usermanager.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.usermanager.exception.UserNotFoundException;
import com.test.usermanager.model.UserDetails;
import com.test.usermanager.repository.UserDetailsRepository;

@RestController
public class SessionController {

	@Autowired
	private UserDetailsRepository repo;

	@PostMapping("/login")
	public UserDetails login(@RequestBody Map<String, String> input) {
		String phone = input.get("phone");
		String password = input.get("password");
		Optional<UserDetails> userOpt = repo.findByPhone(phone);
		if (!userOpt.isPresent()) {
			throw new UserNotFoundException();
		}
		UserDetails user = userOpt.get();
		if (!password.equals(user.getPassword())) {
			throw new InvalidPasswordException();
		}
		return user;
	}

}
