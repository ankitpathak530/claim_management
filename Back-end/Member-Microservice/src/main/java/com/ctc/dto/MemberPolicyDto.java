package com.ctc.dto;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MemberPolicyDto {
	
	@Id
	@Column(name="MID")
	private String memberId;
	
	@Column(name="PID")
	private Long policyId;
	
	@Column(name="tenure")
	private int tenure;
	
	@Column(name="premium_last_date")
	private LocalDate premiumLastDate;
	
	@Column(name="subscription_date")
	private LocalDate subscriptionDate;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<BenefitsDto> benifits;
}
