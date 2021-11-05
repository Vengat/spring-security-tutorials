package com.vengat.tuts.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
/**
 * 
 * 
 * The methods in projecy confog could be seperated into user management and WebAuthorizationConfig
 * 
 * 
 * @author vengatramanan
 *
 */
public class UserManagementConfig {
	
	@Bean
	public UserDetailsService userDetailsService() {
		var userDetailsService = 
				new InMemoryUserDetailsManager();

		var user = User.withUsername("vengat")
				.password("test").authorities("read")
				.build();

		userDetailsService.createUser(user);

		return userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
