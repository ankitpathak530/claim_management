package com.ctc.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ctc.exception.MemberNotFoundException;
import com.ctc.exception.PolicyNotFoundException;
import com.ctc.model.Benefits;
import com.ctc.model.MemberPolicy;
import com.ctc.model.Policy;
import com.ctc.model.PolicyCategory;
import com.ctc.model.ProviderPolicy;
import com.ctc.repo.PolicyRepository;


/**
 * @author ankit pathak
 *
 */
@SpringBootTest(classes = {PolicyServiceTest.class})
public class PolicyServiceTest {

	@Mock
	private PolicyRepository policyRepository;	
	
	@Mock
	private MemberPolicyService memberPolicyService;
	
	@InjectMocks
	private PolicyService policyService;
	
	
	

	private static List<PolicyCategory> listPolicyType = List.of(
			new PolicyCategory("HP1", "HEALTH"),
			new PolicyCategory("TP1", "TRAVEL"), 
			new PolicyCategory("PP1", "PROPERTY"),
			new PolicyCategory("MP1", "MOBILE"),
			new PolicyCategory("CP1", "CYCLE"), 
			new PolicyCategory("MP1", "MOTOR"),
			new PolicyCategory("LP1", "LIFE")
	);
	
	private static List<ProviderPolicy> healthPoliciesProviders = List.of(
			new ProviderPolicy(1006, "DEV HOSPITAL", "9879898989", "Aurangabad", "Lane:2 Aurangabad,Bihar"),
			new ProviderPolicy(1008, "Hanuman Hospital", "8089898989", "Aurangabad", "Lane:2 Aurangabad,Bihar"),
			new ProviderPolicy(1009, "Gupta Gotia Nikalne Wala Hostpital", "8089898989", "Near Dharmsala Aurangabad","Lane:2 Aurangabad,Bihar"),
			new ProviderPolicy(10010, "JK Hospital", "8189898989", "Aurangabad", "Lane:2 Aurangabad,Bihar"),
			new ProviderPolicy(10011, "Raiwala Kidni broker Hospital", "7089898989", "Aurangabad","Lane:2 Aurangabad,Bihar")
	);
	
	private static List<Benefits> healthBenifits = List.of(
			new Benefits("5001","Head Injury",2000.0,50000.0),
			new Benefits("5002","Hand Broken",300,3500),
			new Benefits("5003","Leg Broken",250,3000),
			new Benefits("5004","Heart Attack",3000,100000),
			new Benefits("5005","Kidney Issue",1800,25000)
		);
	
	
	
	@Test
	public void test_FindPolicyProviderByPolicyId() throws PolicyNotFoundException {
		Policy healthPolicy = getHealthPolicy();	
		when(policyRepository.findBypolicyNumber(101l)).thenReturn(Optional.of(healthPolicy)); //Mocking
		assertEquals(5,this.policyService.findPolicyProviderByPolicyId(101l).size());
	}
	
	@Test
	public void test_getPolicyBenefitsByPolicyId() throws PolicyNotFoundException, MemberNotFoundException {
		Policy healthPolicy = getHealthPolicy();	
	    MemberPolicy memberPolicy = getMemberPolicy();
		
		when(policyRepository.findBypolicyNumber(101l)).thenReturn(Optional.of(healthPolicy));
		when(this.memberPolicyService.getMemberPolicy("19271155")).thenReturn(memberPolicy);
		assertEquals(5, this.policyService.getPolicyBenefitsByPolicyId(101l).size());
	}
	
	
	@Test
	public void test_getPolicyBenefitsOfMemberByPolicyIdMemberId() throws MemberNotFoundException, PolicyNotFoundException, ParseException {
		Policy healthPolicy = getHealthPolicy();
		MemberPolicy memberPolicy = getMemberPolicy();
			
		when(policyRepository.findBypolicyNumber(101l)).thenReturn(Optional.of(healthPolicy));
		when(this.memberPolicyService.getMemberPolicy("19271155")).thenReturn(memberPolicy);
		assertEquals(5, this.policyService.getPolicyBenefitsOfMemberByPolicyIdMemberId(101l, "19271155").size());
	}
	  
	@Test
	public void test_getEligibleClaimAmount() throws MemberNotFoundException {
		Policy healthPolicy = getHealthPolicy();
		MemberPolicy memberPolicy = getMemberPolicy();
			
		when(policyRepository.findBypolicyNumber(101l)).thenReturn(Optional.of(healthPolicy));
		when(this.memberPolicyService.getMemberPolicy("19271155")).thenReturn(memberPolicy);
		
		double eligibleClaimAmount = memberPolicy.getBenifits().stream().mapToDouble(e->e.getClaimedAmount()).sum();
		assertEquals(181500.0, eligibleClaimAmount);
	}
	
	
	
	private Policy getHealthPolicy() {
		double totalPremium =  healthBenifits.stream().mapToDouble(e->e.getPremium()).sum();
		double totalClaimAmount = healthBenifits.stream().mapToDouble(e->e.getClaimedAmount()).sum();
		return Policy.builder()
                .policyNumber(101l)
                .benefits(healthBenifits)
                .policyPremium(totalPremium)
                .policyTenure(5)
                .policyClaimAmount(totalClaimAmount)
                .policyType(listPolicyType.get(0))
                .policyProvider(healthPoliciesProviders)
                .build();
	}
	private MemberPolicy getMemberPolicy() {
		return MemberPolicy.builder()
                .memberId("19271155")
                .policyId(101l)
                .tenure(5)
                .subscriptionDate(LocalDate.now())
                .premiumLastDate(LocalDate.now().plusYears(5))
                .benifits(healthBenifits)
                .build();
	}
	
	
}
