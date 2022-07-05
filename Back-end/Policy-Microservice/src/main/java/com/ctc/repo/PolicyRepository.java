package com.ctc.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctc.model.Policy;

/**
 * @author ankit pathak
 *
 */
@Repository
public interface PolicyRepository extends JpaRepository<Policy,Long>{

	
	
	public Optional<Policy> findBypolicyNumber(Long policyId);
}
