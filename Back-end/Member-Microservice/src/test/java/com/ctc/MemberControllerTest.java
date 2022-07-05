package com.ctc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ctc.controller.MemberController;
import com.ctc.dto.BenefitsDto;
import com.ctc.dto.ClaimRequest;
import com.ctc.dto.ClaimResponse;
import com.ctc.dto.MemberPolicyDto;
import com.ctc.exception.ClaimNotFoundException;
import com.ctc.feignclient.AuthorizationClient;
import com.ctc.feignclient.ClaimClient;
import com.ctc.feignclient.PolicyClient;
import com.ctc.model.MemberPolicy;
import com.google.gson.Gson;

/**
 * @author ankit pathak
 *
 */
@AutoConfigureMockMvc
@ContextConfiguration
@ComponentScan(basePackages = "com.ctc")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= {MemberControllerTest.class})
public class MemberControllerTest {

	
	@Mock
	private AuthorizationClient authorizationClient;
	
	@Mock
	private PolicyClient policyClient;
	
	@Mock
	private ClaimClient claimClient;

	@Mock
	private AuthorizationClient authClient;
	
	@InjectMocks
	private MemberController memberController;
	
	@Autowired
	private MockMvc mockMvc;
	
	private String authorization = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbmtpdCIsImV4cCI6MTY1NzAxMTM1MywiaWF0IjoxNjU3MDEwNDUzfQ.Y0Wwg6U0_HpPsrUvmi_kt-JJsk1per3my1iiGAT5PKVcqvF-ySBInmlQcDjqyDgevWvsgw-s5Uu0bp-tfpfxRQ";
	
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
	}
	
	@Order(1)
	@Test
	public void test_viewBills() throws Exception {
		String json2 = "{\r\n"
				+ "    \"memberId\":\"19271155\",\r\n"
				+ "    \"policyNumber\":101\r\n"
				+ "}";
		when(authorizationClient.getUserId(authorization)).thenReturn(101);
		when(this.policyClient.getMemberPolicy("19271155", authorization)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(memberPolicyDto));
        mockMvc.perform(get("/member/viewBills")
        		.header("Authorization", authorization)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json2))
               .andExpect(jsonPath(".tenure").value(5))
               .andExpect(status().isOk())
               .andDo(print());         
	}

	
	@Order(2)
	@Test
	public void test_getClaimStatus() throws Exception {
		
		ClaimResponse claimResponse = new ClaimResponse("Under Dispute","Contradictory details found");
		
		
		when(authorizationClient.getUserId(authorization)).thenReturn(101);
		when(this.claimClient.getClaimStatus(9090l, authorization)).thenReturn(ResponseEntity.ok(claimResponse));
		
		mockMvc.perform(get("/member/getClaimStatus/{claimNumber}",9090l).header("Authorization", authorization))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath(".status").value("Under Dispute"))
		       .andDo(print());
		
	}
	
	
	@Order(3)
	@Test
	public void test_submitClaim() throws Exception {
		
		ClaimRequest claimRequest =  ClaimRequest.builder()
												.claimNumber(9091l)
				                                .policyNumber(101l)
				                                .memberId("19271155")
				                                .amountClaimed(15660)
				                                .benefitsAvailedId("5001")
				                                .providerId(1006)
				                                .build();
		String jsonClaimRequest = "{\r\n"
				+ "    \"claimNumber\":9091,\r\n"
				+ "    \"policyNumber\":101,\r\n"
				+ "    \"memberId\":\"19271155\",\r\n"
				+ "    \"amountClaimed\":15660,\r\n"
				+ "    \"benefitsAvailedId\":\"5001\",\r\n"
				+ "    \"providerId\":1006\r\n"
				+ "}";
		
		ClaimResponse claimResponse1 = new ClaimResponse("Pending Action","We have received your claim. It is in process.");
	   
		when(authorizationClient.getUserId(authorization)).thenReturn(101);
		when(this.claimClient.submitClaim(claimRequest, authorization)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(claimResponse1));
		
		mockMvc.perform(post("/member/submitClaim")
				.header("Authorization", authorization)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonClaimRequest)) 
		        .andExpect(status().isOk())
		      //.andExpect(jsonPath(".status").value("Pending Action"))
		      //.andExpect(jsonPath(".statusDescription").value("We have received your claim. It is in process."))
		        .andDo(print());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static List<BenefitsDto> healthBenifits = List.of(
			new BenefitsDto("5001","Head Injury",2000.0,50000.0),
			new BenefitsDto("5002","Hand Broken",300,3500),
			new BenefitsDto("5003","Leg Broken",250,3000),
			new BenefitsDto("5004","Heart Attack",3000,100000),
			new BenefitsDto("5005","Kidney Issue",1800,25000)
		);
	
   private static MemberPolicyDto memberPolicyDto = MemberPolicyDto.builder()
           .memberId("19271155")
           .policyId(101l)
           .tenure(5)
           .subscriptionDate(LocalDate.now())
           .premiumLastDate(LocalDate.now().plusYears(5))
           .benifits(healthBenifits)
           .build();
  
}
