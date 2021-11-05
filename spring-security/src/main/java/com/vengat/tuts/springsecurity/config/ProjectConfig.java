package com.vengat.tuts.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.vengat.tuts.springsecurity.auth.CustomAuthenticationProvider;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
	

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

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic();


		//All the requests require authentication
		httpSecurity.authorizeRequests()
		.anyRequest().authenticated();

		//None of the requests need to be authenticated
		httpSecurity.authorizeRequests()
		.anyRequest().permitAll();
	}

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	/**
	 * This method does away with all the above methods. We could just override the method instead of 
	 * UserDetailsService & PasswordEncoder bean. Could be used with the configure(HttpSecurity httpSecurity)  method together
	 * 
	 * ❶ Declares a UserDetailsSevice to store the users in memory
	 * ❷ Defines a user with all its details
	 * ❸ Adds the user to be managed by our UserDetailsSevice
	 * ❹ The UserDetailsService and PasswordEncoder are now set up within the configure() method.
	 * 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) 
			throws Exception {
// This block of code is one way of doing
		var userDetailsService = new InMemoryUserDetailsManager();

		var user = User.withUsername("vengat").password("test")
				.authorities("read").build();

		userDetailsService.createUser(user);

		auth.userDetailsService(userDetailsService)
		.passwordEncoder(NoOpPasswordEncoder.getInstance());
//		
		
		//The following block is the other way of doing
		
		auth.authenticationProvider(customAuthenticationProvider);
		
	}

}