package com.ctc.dto;


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
public class ProviderPolicyDto {

	private Integer providerId;
	private String providerName;
	private String providerContact;
	private String providerCity;
	private String providerAddress;
}
