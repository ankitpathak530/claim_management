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
public class Member {

	@Id
	private String memberId;
	private String memberName;
	private long memberContact;
	private String memberCity;
	private String memberEmail;
	private String memberUsername;
	private String memberPassword;
}
