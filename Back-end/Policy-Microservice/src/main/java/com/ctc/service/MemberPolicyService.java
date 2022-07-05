package com.ctc.service;

import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ctc.exception.MemberNotFoundException;
import com.ctc.model.MemberPolicy;
import com.ctc.repo.MemberPolicyRepository;

/**
 * @author ankit pathak
 *
 */
@Service
@Slf4j
public class MemberPolicyService {

	
	@Autowired
	private MemberPolicyRepository memberPolicyRepository;
	
	public boolean isPremiumExpired(String memberId) throws MemberNotFoundException, ParseException
	{
           log.info("MemberPolicyService isPremiumExpired started...");
	       MemberPolicy memberPolicy = this.memberPolicyRepository.findById(memberId).orElseThrow(()-> new MemberNotFoundException("Member Not found"+memberId));
	     
	       
	       LocalDate lastDate = memberPolicy.getPremiumLastDate();     
	       LocalDate curDate  = LocalDate.now();

	       if(lastDate.isAfter(curDate)){
	    	   log.warn("MemberPolicyService isPremiumExpired completed...Policy Expired");
	    	   return false;
	       }
	       log.info("MemberPolicyService isPremiumExpired method completed.");
	       return true;
	}
	
	public MemberPolicy getMemberPolicy(String memberId) throws MemberNotFoundException {
		 log.info("MemberPolicyService getMemberPolicy started...");
		return this.memberPolicyRepository.findById(memberId).orElseThrow(()-> new MemberNotFoundException("Member Not Valid"));
	}
	
	
}
