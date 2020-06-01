package com.test.usermanager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.usermanager.exception.UserNotFoundException;
import com.test.usermanager.model.UserDetails;
import com.test.usermanager.repository.UserDetailsRepository;

@RestController
public class UserController {

	@Autowired
	private UserDetailsRepository repo;

	@GetMapping("/user")
	public Map<String, String> user(@AuthenticationPrincipal OAuth2User principal) {
		Map<String, String> ret = new HashMap<>();
		String name = principal.getAttribute("login");
		ret.put("name", name);
		Optional<UserDetails> userOpt = repo.findByName(name);
		if (userOpt.isPresent()) {
			ret.put("phone", userOpt.get().getPhone());
			return ret;
		}
		throw new UserNotFoundException();
	}

	@GetMapping("/user/{id}")
	public UserDetails fetchUser(@PathVariable Long id) {
		return repo.findById(id).orElseThrow(() -> new UserNotFoundException());
	}

	@GetMapping("/user/all")
	public List<UserDetails> fetchAllUsers() {
		return repo.findAll();
	}

	@PutMapping("/user/set_password")
	public UserDetails setPassword(@RequestBody Map<String, String> input) {
		String oldPass = input.get("oldPass");
		String newPass = input.get("newPass");
		String name = input.get("name");
		Optional<UserDetails> userOpt = repo.findByName(name);
		if (!userOpt.isPresent()) {
			throw new UserNotFoundException();
		}
		UserDetails user = userOpt.get();
		if (!StringUtils.isEmpty(user.getPassword()) && !user.getPassword().equals(oldPass)) {
			throw new InvalidPasswordException();
		}
		user.setPassword(newPass);
		repo.save(user);
		return user;
	}

	@PostMapping("/user/completeReg")
	public UserDetails completeReg(@AuthenticationPrincipal OAuth2User principal,
			@RequestBody Map<String, String> input) {
		String name = principal.getAttribute("login");
		Optional<UserDetails> userOpt = repo.findByName(name);
		if (!userOpt.isPresent()) {
			throw new UserNotFoundException();
		}
		UserDetails user = userOpt.get();
		user.setPhone(input.get("phone"));
		user.setPassword(input.get("password"));
		repo.save(user);
		return user;
	}

	@GetMapping("/user/search/{phone}")
	public UserDetails searchByPhone(@PathVariable String phone) {
		return repo.findByPhone(phone).orElseThrow(() -> new UserNotFoundException());
	}

}
