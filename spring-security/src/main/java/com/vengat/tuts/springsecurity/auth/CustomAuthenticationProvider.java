package com.vengat.tuts.springsecurity.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	 @Autowired
	  private UserDetailsService userDetailsService;

	  @Autowired
	  private PasswordEncoder passwordEncoder;


	/**
	 * ❶ The getName() method is inherited by Authentication from the Principal interface.
	 * ❷ This condition generally calls UserDetailsService and PasswordEncoder to test the username and password.
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = String.valueOf(authentication.getCredentials());
		
//		if("vengat".equals(username) && 
//				"test".equals(password)) {			
//			return new UsernamePasswordAuthenticationToken(username, 
//					password, Arrays.asList());
//		} else {
//			throw new AuthenticationCredentialsNotFoundException("Error in authentication!");
//		}
		
		UserDetails u = userDetailsService.loadUserByUsername(username);

	    if (passwordEncoder.matches(password, u.getPassword())) {
	      return new UsernamePasswordAuthenticationToken(
	            username, 
	            password, 
	            u.getAuthorities());                
	    } else {
	      throw new BadCredentialsException
	                  ("Something went wrong!");    
	    }

	}

	@Override
	public boolean supports(Class<?> authenticationType) {
		//return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationType);
		return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
	}

}
