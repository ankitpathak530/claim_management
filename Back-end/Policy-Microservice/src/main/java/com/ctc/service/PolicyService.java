package com.ctc.service;

import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctc.exception.MemberNotFoundException;
import com.ctc.exception.PolicyNotFoundException;
import com.ctc.model.Benefits;
import com.ctc.model.MemberPolicy;
import com.ctc.model.Policy;
import com.ctc.model.ProviderPolicy;
import com.ctc.repo.PolicyRepository;


/**
 * @author ankit pathak
 *
 */
@Service
@Slf4j
public class PolicyService {
	
	@Autowired
	private PolicyRepository policyRepository;

	@Autowired
	private MemberPolicyService memberPolicyService;
	
	
	@Transactional
	public List<ProviderPolicy> findPolicyProviderByPolicyId(Long policyId) throws PolicyNotFoundException
	{
          log.info("Policy Service findPolicyProviderByPolicyId started...");
		  Policy policy = this.policyRepository.findBypolicyNumber(policyId).orElseThrow(()-> new PolicyNotFoundException("Policy Not Found "+policyId) );	 

	      List<ProviderPolicy> policyProvider = policy.getPolicyProvider();
	      
	      //Sorting Policy Provider based on the city name
	      List<ProviderPolicy> sortedPolicyProvider = policyProvider.stream().sorted((a,b)->a.getProviderCity().compareTo(b.getProviderCity())).collect(Collectors.toList());
	     
		  log.info("Policy Service findPolicyProviderByPolicyId PolicyProvider we found is as follow:="+policy);
	      log.info("Policy Service findPolicyProviderByPolicyId completed.");
	      return sortedPolicyProvider;
	}
	
	
	
	@Transactional
	public List<Benefits> getPolicyBenefitsByPolicyId(Long policyId) throws PolicyNotFoundException
	{
		  log.info("Policy Service getPolicyBenefitsByPolicyId started...");
		  Policy policy = this.policyRepository.findBypolicyNumber(policyId).orElseThrow(()-> new PolicyNotFoundException("Policy Not Found "+policyId) );	 
		  log.info("Policy Service getPolicyBenefitsByPolicyId completed");
		  return policy.getBenefits();
	}
	
	
	@Transactional
	public List<Benefits> getPolicyBenefitsOfMemberByPolicyIdMemberId(Long policyId, String memberId)
			throws PolicyNotFoundException, MemberNotFoundException, ParseException {
		
		log.info("Policy Service getPolicyBenefitsOfMemberByPolicyIdMemberId started...");
		this.policyRepository.findBypolicyNumber(policyId)
				.orElseThrow(() -> new PolicyNotFoundException("Policy Not Found " + policyId));

		MemberPolicy memberPolicy = this.memberPolicyService.getMemberPolicy(memberId);
		if (!this.memberPolicyService.isPremiumExpired(memberId)) {
			log.info("Policy Service getPolicyBenefitsOfMemberByPolicyIdMemberId completed.");
			return memberPolicy.getBenifits();
		}
		log.warn("Policy Service getPolicyBenefitsOfMemberByPolicyIdMemberId completed. Something Went Wrong");
		return null;
	}
	
	
	
	
	@Transactional
	public Double getEligibleClaimAmount(Long policyId,String memberId) throws PolicyNotFoundException, MemberNotFoundException, ParseException
	{
		 log.info("Policy Service getEligibleClaimAmount started...");
		 this.policyRepository.findBypolicyNumber(policyId).orElseThrow(()-> new PolicyNotFoundException("Policy Not Found "+policyId) );	
		  
		  MemberPolicy memberPolicy = this.memberPolicyService.getMemberPolicy(memberId);
			  if(!this.memberPolicyService.isPremiumExpired(memberId)) {
				  log.info("Policy Service getEligibleClaimAmount completed.");
				 return memberPolicy.getBenifits().stream().mapToDouble(e->e.getClaimedAmount()).sum();
			  }  
			  log.warn("Policy Service getEligibleClaimAmount completed. Something went wrong check Expiray of premium");
	     return 0.0;
	}
	
	
}
