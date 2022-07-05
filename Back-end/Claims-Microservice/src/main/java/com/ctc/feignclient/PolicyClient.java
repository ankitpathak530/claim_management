package com.ctc.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ctc.dto.BenefitsDto;
import com.ctc.dto.ProviderPolicyDto;

/**
 * @author ankit pathak
 *
 */
@FeignClient(url="http://localhost:8999/policy-service/policy",name="POLICY-SERVICE")
public interface PolicyClient {

	
	@GetMapping(path="/getEligibleClaimAmount/{policyId}/{memberId}")
	public ResponseEntity<Double> getEligibleClaimAmount(@PathVariable("policyId") Long policyId,@PathVariable("memberId") String memberId,@RequestHeader("Authorization") String authorization);

	@GetMapping(path="/getPolicyBenefits/{policyId}/{memberId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BenefitsDto>> getPolicyBenefitsOfMemberByPolicyIdMemberId(@PathVariable("policyId") Long policyId,@PathVariable("memberId") String memberId,@RequestHeader("Authorization") String authorization);
	
	@GetMapping(path="/getChainOfProviders/{policyId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProviderPolicyDto>> getChainOfProviders(@PathVariable("policyId") Long policyId,@RequestHeader("Authorization") String authorization);
	
	
	
}
