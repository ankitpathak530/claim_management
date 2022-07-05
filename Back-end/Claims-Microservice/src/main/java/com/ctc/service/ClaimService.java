package com.ctc.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctc.dto.ClaimRequest;
import com.ctc.dto.ClaimResponse;
import com.ctc.exception.ClaimNotFoundException;
import com.ctc.model.Claim;
import com.ctc.repo.ClaimRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ankit pathak
 *
 */
@Service
@Slf4j
public class ClaimService {

	@Autowired
	private ClaimRepository claimRepository;
	
	@Transactional
	public ClaimResponse getClaim(Long claimNumber) throws ClaimNotFoundException {
		 log.info("Claim Service getClaim started...");
		 Claim claim = this.claimRepository.findById(claimNumber).orElseThrow( ()-> new ClaimNotFoundException("Claim Not Found"));
         return new ClaimResponse(claim.getStatus(),claim.getRemarks());
	}
	
	@Transactional
	public ClaimResponse SaveClaim(ClaimRequest claimRequest) throws ClaimNotFoundException {
		log.info("Claim Service SaveClaim started...");
		Claim claim = this.claimRepository.save(requestToClaim(claimRequest));
		return new ClaimResponse(claim.getStatus(),claim.getRemarks());
	}
	
	
	private Claim requestToClaim(ClaimRequest req) {
		new Claim();
		return Claim.builder()
		             .claimNumber(req.getClaimNumber())
		             .status(req.getStatus())
		             .remarks(req.getRemarks())
		             .policyNumber(req.getPolicyNumber())
		             .providerId(req.getProviderId())
		             .memberId(req.getMemberId())
		             .benefitsAvailedId(req.getBenefitsAvailedId())
		             .amountClaimed(req.getAmountClaimed())
		             .settled(req.isSettled()).build();
	}
	
}
