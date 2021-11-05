package com.vengat.tuts.springsecurity.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class WebAuthorizationConfig  extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic();


		//All the requests require authentication
		httpSecurity.authorizeRequests()
		.anyRequest().authenticated();

		//None of the requests need to be authenticated
//		httpSecurity.authorizeRequests()
//		.anyRequest().permitAll();
	}


}
