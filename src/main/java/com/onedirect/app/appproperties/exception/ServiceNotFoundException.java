package com.onedirect.app.appproperties.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,reason ="Service not available in the app")
public class ServiceNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
