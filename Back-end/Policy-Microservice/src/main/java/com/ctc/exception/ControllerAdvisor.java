package com.ctc.exception;


import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalDate;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * @author ankit pathak
 *
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler(PolicyNotFoundException.class)
	public ResponseEntity<Object> handleCityNotFoundException(
			PolicyNotFoundException ex, WebRequest request) {

	    Map<String, Object> body = new LinkedHashMap<>();
	    body.put("timestamp", LocalDate.now());
	    body.put("message", "Policy Not Found");

	    return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(body);
	}
	
	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<Object> handleMemberNotFoundException(MemberNotFoundException ex,WebRequest request){
		  Map<String, Object> body = new LinkedHashMap<>();
		    body.put("timestamp", LocalDate.now());
		    body.put("message", "Member Not Found");

		    return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(body);
	}

}
