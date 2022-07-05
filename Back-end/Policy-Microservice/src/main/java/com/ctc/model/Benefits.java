package com.ctc.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
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
@Entity
@Table(name="benefits")
public class Benefits {

	@Id
	@Column(name="BID")
	private String benefitId;
	
	@Column(name="Name")
	private String benefitName;
	private double premium;
	private double claimedAmount;
	

	@JsonIgnore	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL,mappedBy = "benefits")
	private Set<Policy> policyBenefits = new HashSet<>();
	
	@ManyToOne
	private MemberPolicy member;
	
	

	public Benefits(String benefitId, String benefitName, double premium, double claimedAmount) {
		super();
		this.benefitId = benefitId;
		this.benefitName = benefitName;
		this.premium = premium;
		this.claimedAmount = claimedAmount;
	}



	@Override
	public String toString() {
		return "Benefits [benefitId=" + benefitId + ", benefitName=" + benefitName + ", premium=" + premium
				+ ", claimedAmount=" + claimedAmount + "]";
	}

}