package com.cts.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.authorization.model.User;
import com.cts.authorization.repository.UserRepository;

/**
 * @author ankit pathak
 *
 */
@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;

	public int findUserId(String uname) {
		User user=userRepo.findByUsername(uname);
		return user.getId();
	}

}
