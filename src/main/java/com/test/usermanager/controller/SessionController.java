package com.test.usermanager.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.usermanager.model.Userdet;
import com.test.usermanager.repository.UserdetRepository;

@RestController
public class SessionController {

	@Autowired
	private UserdetRepository repo;

	@PostMapping("/login")
	public Userdet login(@RequestBody Map<String, String> input) {
		String phone = input.get("phone");
		String password = input.get("password");
		Optional<Userdet> userOpt = repo.findByPhone(phone);
		if (!userOpt.isPresent()) {
			throw new RuntimeException("Invalid phone number.");
		}
		Userdet user = userOpt.get();
		if (!password.equals(user.getPassword())) {
			throw new RuntimeException("Invalid password.");
		}
		return user;
	}

}
