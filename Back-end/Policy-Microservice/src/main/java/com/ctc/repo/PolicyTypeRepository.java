package com.ctc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctc.model.PolicyCategory;

/**
 * @author ankit pathak
 *
 */
public interface PolicyTypeRepository extends JpaRepository<PolicyCategory,String> {

}
