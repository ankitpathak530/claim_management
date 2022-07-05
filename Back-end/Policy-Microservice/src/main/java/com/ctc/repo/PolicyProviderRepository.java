package com.ctc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctc.model.ProviderPolicy;

/**
 * @author ankit pathak
 *
 */
public interface PolicyProviderRepository extends JpaRepository<ProviderPolicy, Integer> {

}
