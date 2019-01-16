package com.luist.todo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {
	
	private AuthUserLookUpService userLookupService;

	public AuthUserDetailsService(AuthUserLookUpService userLookupService) {
		this.userLookupService = userLookupService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userLookupService.findUser(username);
	}

}
