package com.ctc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctc.model.MemberPolicy;

/**
 * @author ankit pathak
 *
 */
public interface MemberPolicyRepository extends JpaRepository<MemberPolicy, String> {

}
