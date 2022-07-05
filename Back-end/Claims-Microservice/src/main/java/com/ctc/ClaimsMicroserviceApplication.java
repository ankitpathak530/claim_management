package com.ctc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import com.ctc.model.Claim;
import com.ctc.repo.ClaimRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableSwagger2
public class ClaimsMicroserviceApplication {

	
	private static ClaimRepository claimRepository;

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ClaimsMicroserviceApplication.class, args);
		claimRepository = run.getBean(ClaimRepository.class);
		
		saveClaim();
		
	}


	private static void saveClaim() {

		Claim claim = Claim.builder() 
				                    .claimNumber(9090L)
				                    .status("Under Dispute")
				                    .remarks("Contradictory details found")
				                    .policyNumber(101l)
				                    .providerId(1006)
				                    .memberId("19271155l")   
				                    .benefitsAvailedId("5001")
				                    .amountClaimed(89000)
				                    .settled(false)
				                    .build();
		
	 
		claimRepository.save(claim);
		
	}

}
