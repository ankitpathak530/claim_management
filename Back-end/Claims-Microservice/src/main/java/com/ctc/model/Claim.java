package com.ctc.model;

import javax.persistence.Entity;
import javax.persistence.Id;

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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class Claim {

	@Id
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
