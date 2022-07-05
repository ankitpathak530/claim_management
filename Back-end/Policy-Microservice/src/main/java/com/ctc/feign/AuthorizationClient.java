package com.ctc.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


/**
 * @author ankit pathak
 *
 */
@FeignClient(url = "http://localhost:8999/authorization-service", name = "AUTHORIZATION-SERVICE")
public interface AuthorizationClient {
	
	@GetMapping("/getuname")
	public String getUname(@RequestHeader("Authorization") String authorization);

}
