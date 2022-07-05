package com.ctc.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author ankit pathak
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProviderPolicy {

	@Id
	private Integer providerId;
	private String providerName;
	private String providerContact;
	private String providerCity;
	private String providerAddress;
	
	@ManyToOne
    private Policy policy;
	
	public ProviderPolicy(Integer providerId, String providerName, String providerContact, String providerCity,
			String providerAddress) {
		super();
		this.providerId = providerId;
		this.providerName = providerName;
		this.providerContact = providerContact;
		this.providerCity = providerCity;
		this.providerAddress = providerAddress;
	}

	@Override
	public String toString() {
		return "ProviderPolicy [providerId=" + providerId + ", providerName=" + providerName + ", providerContact="
				+ providerContact + ", providerCity=" + providerCity + ", providerAddress=" + providerAddress + "]";
	}
	
	
    
    
}
