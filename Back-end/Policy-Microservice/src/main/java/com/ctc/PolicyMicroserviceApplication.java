package com.ctc;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import com.ctc.model.Benefits;
import com.ctc.model.MemberPolicy;
import com.ctc.model.Policy;
import com.ctc.model.PolicyCategory;
import com.ctc.model.ProviderPolicy;
import com.ctc.repo.MemberPolicyRepository;
import com.ctc.repo.PolicyProviderRepository;
import com.ctc.repo.PolicyRepository;
import com.ctc.repo.PolicyTypeRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ankit pathak
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableSwagger2
public class PolicyMicroserviceApplication {
	

	private static PolicyRepository policyRepository;
	private static PolicyTypeRepository policyTypeRepository;
	private static PolicyProviderRepository policyProviderRepository;
	private static MemberPolicyRepository memberPolicyRepository;
	
	private static List<PolicyCategory> listPolicyType = List.of(
			new PolicyCategory("HP1", "HEALTH"),
			new PolicyCategory("TP1", "TRAVEL"), 
			new PolicyCategory("PP1", "PROPERTY"),
			new PolicyCategory("MP1", "MOBILE"),
			new PolicyCategory("CP1", "CYCLE"), 
			new PolicyCategory("MP1", "MOTOR"),
			new PolicyCategory("LP1", "LIFE")
	);
	
	
	private static List<ProviderPolicy> motorPoliciesProviders = List.of(
			new ProviderPolicy(1001, "Khemka Motors", "8089898989", "Aurangabad", "Lane:2 Aurangabad,Bihar"),
			new ProviderPolicy(1002, "Sahil Motors", "8089876989", "Aurangabad", "Lane:2 MG-Road Aurangabad,Bihar"),
			new ProviderPolicy(1003, "Parvati Motors", "7689898989", "Aurangabad", "Lane:1 Farm Aurangabad,Bihar"),
			new ProviderPolicy(1004, "Mahindra Motors", "8789898989", "Aurangabad","Lane:1 Baipaas Londu road Aurangabad,Bihar"),
			new ProviderPolicy(1005, "Sunshine Motors", "7659898989", "Aurangabad","Overbridge Near Murgi Chor Farm Aurangabad,Bihar")
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
	private static List<Benefits> motorBenifits = List.of(
			new Benefits("5006","Engine Damage",1200,30000),
			new Benefits("5007","Glass Damage",1000,10000),
			new Benefits("5008","Allow Wheels Damage",1100,15000),
			new Benefits("5009","Scrath Cover",500,8000),
			new Benefits("50010","Back Body Damage",1800,40000)
			);
	  
	
	

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(PolicyMicroserviceApplication.class, args);

		policyRepository = run.getBean(PolicyRepository.class);
		policyTypeRepository = run.getBean(PolicyTypeRepository.class);
		policyProviderRepository = run.getBean(PolicyProviderRepository.class);
		memberPolicyRepository = run.getBean(MemberPolicyRepository.class);
		
		savePolicyProvider();
		savePolicyCategory();
		savePolicy();
	    saveMemberPolicy();
	}
	
	

	private static void savePolicyCategory() {
		listPolicyType.forEach(p -> policyTypeRepository.save(p));
	}

	
	private static void savePolicyProvider() {
		healthPoliciesProviders.forEach(pp -> policyProviderRepository.save(pp));
		motorPoliciesProviders.forEach(pp -> policyProviderRepository.save(pp));
	}
	
	
	
	
	private static void savePolicy() {	
		  //////////////////////////////  HEALTH INSURENCE  ////////////////////////////////	
		
		double totalPremium =  healthBenifits.stream().mapToDouble(e->e.getPremium()).sum();
		double totalClaimAmount = healthBenifits.stream().mapToDouble(e->e.getClaimedAmount()).sum();
		Policy healthPolicy1 = Policy.builder()
				                              .policyNumber(101l)
				                              .benefits(healthBenifits)
				                              .policyPremium(totalPremium)
				                              .policyTenure(5)
				                              .policyClaimAmount(totalClaimAmount)
				                              .policyType(listPolicyType.get(0))
				                              .policyProvider(healthPoliciesProviders)
				                              .build();

	  //////////////////////////////  MOTOR INSURENCE  ////////////////////////////////		
		
		totalPremium =  motorBenifits.stream().mapToDouble(e->e.getPremium()).sum();
	    totalClaimAmount = motorBenifits.stream().mapToDouble(e->e.getClaimedAmount()).sum();
		Policy MotorPolicy1 = Policy.builder()
				                          .policyNumber(102l)
				                          .benefits(motorBenifits)
				                          .policyPremium(totalPremium)
				                          .policyTenure(3)
				                          .policyType(listPolicyType.get(5))
				                          .policyClaimAmount(totalClaimAmount)
				                          .policyProvider(motorPoliciesProviders)
				                          .build();
	

		////////////////////////////  SAVING POLICIS TO DB  ///////////////////////////
		List<Policy> policies = List.of(healthPolicy1,MotorPolicy1);
		policies.forEach(p -> policyRepository.save(p));
	}
	
	private static void saveMemberPolicy() {
		 new MemberPolicy();
		MemberPolicy memberPolicy = MemberPolicy.builder()
		                  .memberId("19271155")
		                  .policyId(101l)
		                  .tenure(5)
		                  .subscriptionDate(LocalDate.now())
		                  .premiumLastDate(LocalDate.now().plusYears(5))
		                  .benifits(healthBenifits)
		                  .build();
		 		 
		 memberPolicyRepository.save(memberPolicy);                 
	}

}
