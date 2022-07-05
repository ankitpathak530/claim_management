package com.ctc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.dto.ClaimRequest;
import com.ctc.dto.ClaimResponse;
import com.ctc.dto.MemberPolicyDto;
import com.ctc.exception.ClaimNotFoundException;
import com.ctc.feignclient.AuthorizationClient;
import com.ctc.feignclient.ClaimClient;
import com.ctc.feignclient.PolicyClient;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ankit pathak
 *
 */
@RestController
@RequestMapping("/member")
@CrossOrigin
@Slf4j
public class MemberController {
	
	@Autowired
	private AuthorizationClient authorizationClient;
	
	@Autowired
	private PolicyClient policyClient;
	
	@Autowired
	private ClaimClient claimClient;

	@Autowired
	private AuthorizationClient authClient;
	
	
	@Autowired
	private Environment env;
	
	
	  @GetMapping("/check")
	  public String healthCheck(@RequestHeader("Authorization") String authorization)
	  {
		  log.info("MemberController healthCheck started...");
		   this.authClient.getUserId(authorization);
		  log.info("MemberController healthCheck completed.");
		   return "Connection is Healthy with Member Microservice running on PORT:"+env.getProperty("local.server.port");
	  }
	
	@GetMapping("/viewBills")
	public ResponseEntity<MemberPolicyDto> viewBills(@RequestBody ClaimRequest claimRequest,@RequestHeader("Authorization") String authorization) {
		authorizationClient.getUserId(authorization);
		return this.policyClient.getMemberPolicy(claimRequest.getMemberId(), authorization);
	}
	
	 @GetMapping("/getClaimStatus/{claimNumber}")
	  public ResponseEntity<ClaimResponse> getClaimStatus(@PathVariable("claimNumber") Long claimNumber,
			                                              @RequestHeader("Authorization") String authorization) throws ClaimNotFoundException {
			authorizationClient.getUserId(authorization);
	  	return this.claimClient.getClaimStatus(claimNumber, authorization);
	}
	
	
	  @PostMapping(value="/submitClaim")
	  public ResponseEntity<ClaimResponse> submitClaim(@RequestBody ClaimRequest claimRequest,
			                                              @RequestHeader("Authorization") String authorization){  
		   log.info("Member Controller SubmitClaim started..."+claimRequest);
			authorizationClient.getUserId(authorization);
		  return this.claimClient.submitClaim(claimRequest, authorization);
	  }
	  
}
