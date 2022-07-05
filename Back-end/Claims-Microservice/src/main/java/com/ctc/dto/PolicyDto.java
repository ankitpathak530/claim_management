package com.ctc.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ankit pathak
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyDto {

	private Long policyNumber;
	private double policyPremium;
	private double policyTenure;
	private List<BenefitsDto> benefits;
	private List<ProviderPolicyDto> policyProviders;
	
}
