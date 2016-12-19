package com.onedirect.app.appproperties.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.onedirect.app.appproperties.exception.CustomAppException;
import com.onedirect.app.appproperties.exception.ErrorResponse;



@ControllerAdvice 
@RestController  
public class GlobalExceptionController {  
	
	private static Logger logger = LogManager.getLogger(GlobalExceptionController.class);
	
	@ExceptionHandler(value = Exception.class)  
    public ResponseEntity<ErrorResponse> handleException(Exception e){  
    	ErrorResponse error = new ErrorResponse();
		error.setErrMsg(e.getMessage());
		error.setErrCode(HttpStatus.INTERNAL_SERVER_ERROR.value()+"");
		logger.error("{}",e);
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR); 
    }  
  
    @ExceptionHandler(value = CustomAppException.class)  
    public ResponseEntity<ErrorResponse> handleCustomAppException(CustomAppException e){
    	ErrorResponse error = new ErrorResponse();
		error.setErrCode(e.getErrCode());
		error.setErrMsg(e.getErrMsg());
		logger.error("{}",e);
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    } 
} 
