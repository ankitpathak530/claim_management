package com.ctc.model;


import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
public class Policy {

	@Id
	private Long policyNumber;
	private double policyPremium;
	private double policyTenure;
    private double policyClaimAmount;
	
	@OneToOne
	private PolicyCategory policyType;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ProviderPolicy> policyProvider;
	
	
	
	@JsonIgnore	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="policy_benefits", joinColumns= @JoinColumn(name="policyNumber"),
					  inverseJoinColumns= @JoinColumn(name="benefitID"))
	private List<Benefits> benefits;

}
