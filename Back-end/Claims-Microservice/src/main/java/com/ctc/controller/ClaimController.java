package com.ctc.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.dto.BenefitsDto;
import com.ctc.dto.ClaimRequest;
import com.ctc.dto.ClaimResponse;
import com.ctc.dto.ProviderPolicyDto;
import com.ctc.exception.ClaimNotFoundException;
import com.ctc.feignclient.AuthorizationClient;
import com.ctc.feignclient.PolicyClient;
import com.ctc.service.ClaimService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author ankit pathak
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/claim")
@Slf4j
public class ClaimController {

	
	  @Autowired
	  private AuthorizationClient authClient;
	
	  @Autowired
	  private PolicyClient policyClient;
	  
	  @Autowired
	  private ClaimService claimService;
	  
      @Autowired
      private Environment env;
	  
	  @GetMapping("/check")
	  public String healthCheck(@RequestHeader("Authorization") String authorization)
	  {
		  log.info("ClaimController healthCheck started...");
		  this.authClient.getUserId(authorization);
		  log.info("ClaimController healthCheck completed.");
			return "Connection is Healthy with Policy Microservice running on PORT:"+env.getProperty("local.server.port");
	  }
	  
	  
	  
	  @GetMapping(value="/getClaimStatus/{claimNumber}")
	  public ResponseEntity<ClaimResponse> getClaimStatus(@PathVariable("claimNumber") Long claimNumber,
			                                              @RequestHeader("Authorization") String authorization) throws ClaimNotFoundException {
		  log.info("ClaimController getClaimStatus started...");
		  this.authClient.getUserId(authorization);
		  return ResponseEntity.status(HttpStatus.OK).body(this.claimService.getClaim(claimNumber));	   
	  }
	  
	  
	  
	  
	  @PostMapping(value="/submitClaim", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<ClaimResponse> submitClaim(@RequestBody ClaimRequest claimRequest,
			                                              @RequestHeader("Authorization") String authorization) throws ClaimNotFoundException {  
		  log.info("ClaimController submitClaim started...");
		  this.authClient.getUserId(authorization);
		
		  try {
				  Double maximumClaimedAmount =  this.policyClient.getEligibleClaimAmount(claimRequest.getPolicyNumber(), claimRequest.getMemberId(), authorization).getBody();
		          List<BenefitsDto> benefits =   this.policyClient.getPolicyBenefitsOfMemberByPolicyIdMemberId(claimRequest.getPolicyNumber(), claimRequest.getMemberId(), authorization).getBody();
				  List<ProviderPolicyDto> policyProviders = this.policyClient.getChainOfProviders(claimRequest.getPolicyNumber(), authorization).getBody();
				
				  boolean condition1 = claimRequest.getAmountClaimed() > maximumClaimedAmount;
				  boolean condition2 = benefits.stream().filter(e->e.getBenefitId().equals(claimRequest.getBenefitsAvailedId())).collect(Collectors.toList()).size()==0;
	              boolean condition3 = policyProviders.stream().filter(e->e.getProviderId().equals(claimRequest.getProviderId())).collect(Collectors.toList()).size()==0;
		

				  System.out.println(condition1+"  -  "+condition2+"    -   "+condition3);
					 if (condition1 || condition2 || condition3) {
						claimRequest.setStatus("Claim Rejected");
						claimRequest.setRemarks("Claim has been rejected!  "
								+ "Check the below details and try again. \n "
								+ "1.Your claim amount is excedded \n "
								+ "2.Policy is not applicable for the selected provider\n"
								+ "3.You have selected to claim benefits that is not available for you");
					   }
						else {
						claimRequest.setStatus("Pending Action");
						claimRequest.setRemarks("We have received your claim. It is in process.");
						}
					
	   	    
		  }catch(Exception e) {
			  log.warn("Exception Occured while Submiting Claim->",e);
			  claimRequest.setStatus("Insufficient Claim Details");
			  claimRequest.setRemarks("Kindly verify your details carefully and try again.");
		  } 
		    ClaimResponse claimResponse = claimService.SaveClaim(claimRequest);
		    log.info("ClaimController submitClaim completed.");
	        return ResponseEntity.status(HttpStatus.OK).body(claimResponse); 
	  }
}
