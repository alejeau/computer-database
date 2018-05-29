package com.excilys.formation.cdb.security.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason="To show an example of a custom message")
public class UsernameTakenException extends RuntimeException {
	
}
