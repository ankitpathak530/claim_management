package com.ctc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;

/**
 * @author ankit pathak
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequest {

	private Long claimNumber;
	private String status;
	private String remarks;
	
	private Long policyNumber;
	private Integer providerId;
	private String memberId;
	
	private String benefitsAvailedId;
	private Integer amountClaimed;
	private boolean settled;
}
