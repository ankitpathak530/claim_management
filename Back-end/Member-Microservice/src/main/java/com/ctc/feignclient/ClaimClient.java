package com.ctc.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ctc.dto.ClaimRequest;
import com.ctc.dto.ClaimResponse;
import com.ctc.exception.ClaimNotFoundException;

/**
 * @author ankit pathak
 *
 */
@FeignClient(url="http://localhost:8999/claim-service/claim",name="CLAIM-SERVICE")
public interface ClaimClient {
	
	
	  
	 @GetMapping("/getClaimStatus/{claimNumber}")
	  public ResponseEntity<ClaimResponse> getClaimStatus(@PathVariable("claimNumber") Long claimNumber,
			                                              @RequestHeader("Authorization") String authorization) throws ClaimNotFoundException;
	
		

	  @PostMapping("/submitClaim")
	  public ResponseEntity<ClaimResponse> submitClaim(@RequestBody ClaimRequest claimRequest,
			                                              @RequestHeader("Authorization") String authorization); 
}
