package com.ctc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ctc.model.Claim;


/**
 * @author ankit pathak
 *
 */
@Repository
public interface ClaimRepository extends JpaRepository<Claim,Long>{

}
