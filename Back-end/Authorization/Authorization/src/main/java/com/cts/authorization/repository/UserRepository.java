package com.cts.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.authorization.model.User;

/**
 * @author ankit pathak
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String uname);
	
}