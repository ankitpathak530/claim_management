package com.ctc.controller;

import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.exception.MemberNotFoundException;
import com.ctc.exception.PolicyNotFoundException;
import com.ctc.feign.AuthorizationClient;
import com.ctc.model.Benefits;
import com.ctc.model.MemberPolicy;
import com.ctc.model.ProviderPolicy;
import com.ctc.service.MemberPolicyService;
import com.ctc.service.PolicyService;



/**
 * @author ankit pathak
 *
 */
@RestController
@RequestMapping("/policy")
@Slf4j
public class PolicyController {

	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private MemberPolicyService memberPolicyService;
	
	
	@Autowired
	private Environment env;
	
	
	@Autowired
	private AuthorizationClient authorizationClient;
	
	@GetMapping("/check")
	public String healthCheck(@RequestHeader("Authorization") String authorization) {
		log.info("Policy-Controller health-check start...");
		authorizationClient.getUname(authorization);
		log.info("Policy-Controller health-check completed");
		return "Connection is Healthy with Policy Microservice running on PORT:"+env.getProperty("local.server.port");
	}
	
	
	
	@GetMapping(value="/getChainOfProviders/{policyId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProviderPolicy>> getChainOfProviders(@PathVariable("policyId") Long policyId,@RequestHeader("Authorization") String authorization) throws PolicyNotFoundException
	{
		log.info("Policy-Controller Chain-Of-providers start...");
		authorizationClient.getUname(authorization);
		return  ResponseEntity.status(HttpStatus.OK).body(policyService.findPolicyProviderByPolicyId(policyId));
	}
	
	
	
	@GetMapping(value="/getPolicyBenefits/{policyId}/{memberId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Benefits>> getPolicyBenefitsOfMemberByPolicyIdMemberId(@PathVariable("policyId") Long policyId,@PathVariable("memberId") String memberId,@RequestHeader("Authorization") String authorization) throws PolicyNotFoundException, MemberNotFoundException, ParseException
	{
		log.info("Policy-Controller get policy benifits start...");
		authorizationClient.getUname(authorization);
		return  ResponseEntity.status(HttpStatus.OK).body(policyService.getPolicyBenefitsOfMemberByPolicyIdMemberId(policyId,memberId));
	}
	
	
	@GetMapping(value="/getMemberPolicy/{memberId}")
	public ResponseEntity<MemberPolicy> getMemberPolicy(@PathVariable("memberId") String memberId,@RequestHeader("Authorization") String authorization) throws MemberNotFoundException{
		authorizationClient.getUname(authorization);
		return  ResponseEntity.status(HttpStatus.OK).body(this.memberPolicyService.getMemberPolicy(memberId));
	}
	
	
	@GetMapping(value="/getEligibleClaimAmount/{policyId}/{memberId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> getEligibleClaimAmount(@PathVariable("policyId") Long policyId,@PathVariable("memberId") String memberId,@RequestHeader("Authorization") String authorization) throws PolicyNotFoundException, MemberNotFoundException, ParseException
	{
		log.info("Policy-Controller get eligible claim amount start...");
		authorizationClient.getUname(authorization);
		return  ResponseEntity.status(HttpStatus.OK).body(policyService.getEligibleClaimAmount(policyId,memberId));
	}
	
	
	
	@GetMapping(value="/getAllPolicyBenefits/{policyId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Benefits>> getAllPolicyBenefitsByPolicyId(@PathVariable("policyId") Long policyId,@RequestHeader("Authorization") String authorization) throws PolicyNotFoundException
	{
		log.info("Policy-Controller all policy benefits start...");
		authorizationClient.getUname(authorization);
		return  ResponseEntity.status(HttpStatus.OK).body(policyService.getPolicyBenefitsByPolicyId(policyId));
	}
}
