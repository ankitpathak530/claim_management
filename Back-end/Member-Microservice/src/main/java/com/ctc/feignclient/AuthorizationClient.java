package com.ctc.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author ankit pathak
 *
 */
@FeignClient(url="http://localhost:8999/authorization-service",value = "authorization-service")
public interface AuthorizationClient {

	@GetMapping("/getid")
	public int getUserId(@RequestHeader("Authorization") String authorization);
	
}
