package com.test.usermanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such user.")
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super(String.format("User not found."));
	}
}
