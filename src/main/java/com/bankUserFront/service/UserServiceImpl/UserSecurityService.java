package com.bankUserFront.service.UserServiceImpl;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankUserFront.domain.User;
import com.bankUserFront.dao.*;

@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	
	private static final Logger  LOG = LoggerFactory.getLogger(UserSecurityService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
        if (null == user) {
            LOG.warn("Username {} not found", username);
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return user;
	}

}
