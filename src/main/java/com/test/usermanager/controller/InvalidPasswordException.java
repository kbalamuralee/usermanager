package com.test.usermanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid password.")
public class InvalidPasswordException extends RuntimeException {

	public InvalidPasswordException() {
		super(String.format("Invalid password."));
	}
}
