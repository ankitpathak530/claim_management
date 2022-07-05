package com.ctc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ctc.exception.MemberNotFoundException;
import com.ctc.model.MemberPolicy;
import com.ctc.repo.MemberPolicyRepository;

/**
 * @author ankit pathak
 *
 */
@SpringBootTest(classes = {MemberPolicyServiceTest.class})
public class MemberPolicyServiceTest {

	@InjectMocks
	private MemberPolicyService memberPolicyService;
	
	@Mock
	private MemberPolicyRepository memberPolicyRepository;
	
	
	
	@Test
	public void test_isPremiumExpired() throws MemberNotFoundException, ParseException {
		MemberPolicy memberPolicy = getMemberPolicy();
		when(this.memberPolicyRepository.findById("19271155")).thenReturn(Optional.of(memberPolicy));
		assertEquals(false, this.memberPolicyService.isPremiumExpired("19271155"));
	}
	
	@Test
	 public void test_getMemberPolicy() throws MemberNotFoundException {
		MemberPolicy memberPolicy = getMemberPolicy();
		when(this.memberPolicyRepository.findById("19271155")).thenReturn(Optional.of(memberPolicy));
		assertEquals(memberPolicy, this.memberPolicyService.getMemberPolicy("19271155"));
	 }
	
	
	
	
	
	
	private MemberPolicy getMemberPolicy() {
		return MemberPolicy.builder()
                .memberId("19271155")
                .policyId(101l)
                .tenure(5)
                .subscriptionDate(LocalDate.now())
                .premiumLastDate(LocalDate.now().plusYears(5))
                .benifits(null)
                .build();
	}
}
