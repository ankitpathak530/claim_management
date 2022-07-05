package com.ctc.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



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
@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = "com.ctc")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes= {PolicyControllerTest.class})
public class PolicyControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private PolicyService policyService;
	
	@Mock
	private MemberPolicyService memberPolicyService;
	
	@Mock
	private AuthorizationClient authorizationClient;
	
	
	@InjectMocks
	private PolicyController policyController;
	
	
	
	
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(policyController).build();
	}
	
	
	@Test
	@Order(1)
	public void test_getChainOfProviders() throws Exception {
	  
		when(policyService.findPolicyProviderByPolicyId(101l)).thenReturn(healthPoliciesProviders);
		when(authorizationClient.getUname("authorization")).thenReturn("token:edfdkfdfdeewf:3dfadfi4:dfkefelrjkd:df453dfds");
		this.mockMvc.perform(get("/policy/getChainOfProviders/{policyId}",101l).header("Authorization", "Bearer:edfdkfdfdeewf:3dfadfi4:dfkefelrjkd:df453dfds"))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$[0].providerName").value("DEV HOSPITAL"))
		       .andDo(print());
	}
	

	@Test
	@Order(2)
	public void test_getPolicyBenefitsOfMemberByPolicyIdMemberId() throws Exception {
		
		when(this.policyService.getPolicyBenefitsOfMemberByPolicyIdMemberId(101l,"19271155")).thenReturn(healthBenifits);	
		when(authorizationClient.getUname("Authorization")).thenReturn("token:edfdkfdfdeewf:3dfadfi4:dfkefelrjkd:df453dfds");
	
	    this.mockMvc.perform(get("/policy/getPolicyBenefits/{policyId}/{memberId}",101l,"19271155").header("Authorization", "Bearer edfdkfdfdeewf:3dfadfi4:dfkefelrjkd:df453dfds"))
	              .andExpect(status().isOk())
	              .andExpect(jsonPath("$[1].benefitName").value("Hand Broken"))
	              .andDo(print());
	}
	
	
	

	@Test
	@Order(3)
	public void test_getMemberPolicy() throws Exception {
	
		when(this.memberPolicyService.getMemberPolicy("19271155")).thenReturn(memberPolicy);
		when(authorizationClient.getUname("Authorization")).thenReturn("token:edfdkfdfdeewf:3dfadfi4:dfkefelrjkd:df453dfds");
		this.mockMvc.perform(get("/policy/getMemberPolicy/{memberId}","19271155").header("Authorization", "Bearer edfdkfdfdeewf:3dfadfi4:dfkefelrjkd:df453dfds"))
		           .andExpect(status().isOk())
		           .andExpect(jsonPath(".policyId").value(101))
		           .andDo(print());
	}
	
	@Test
	@Order(4)
	public void test_getEligibleClaimAmount() throws Exception {
		when(authorizationClient.getUname("Authorization")).thenReturn("token:edfdkfdfdeewf:3dfadfi4:dfkefelrjkd:df453dfds");
		when(this.policyService.getEligibleClaimAmount(101l,"19271155")).thenReturn(181500.0);
		this.mockMvc.perform(get("/policy/getEligibleClaimAmount/{policyId}/{memberId}",101l,"19271155").header("Authorization", "Bearer edfdkfdfdeewf:3dfadfi4:dfkefelrjkd:df453dfds"))
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("$").value(181500.0))
                  .andDo(print());
	}
	
	
	
	@Test
	@Order(5)
	public void test_getAllPolicyBenefitsByPolicyId() throws Exception{
		when(authorizationClient.getUname("Authorization")).thenReturn("token:edfdkfdfdeewf:3dfadfi4:dfkefelrjkd:df453dfds");
		when(this.policyService.getPolicyBenefitsByPolicyId(101l)).thenReturn(healthBenifits);
		this.mockMvc.perform(get("/policy/getAllPolicyBenefits/{policyId}",101l).header("Authorization", "Bearer edfdkfdfdeewf:3dfadfi4:dfkefelrjkd:df453dfds"))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	
	
	
	
	
	
	
	
	
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
	
   private static MemberPolicy memberPolicy = MemberPolicy.builder()
           .memberId("19271155")
           .policyId(101l)
           .tenure(5)
           .subscriptionDate(LocalDate.now())
           .premiumLastDate(LocalDate.now().plusYears(5))
           .benifits(healthBenifits)
           .build();
}
