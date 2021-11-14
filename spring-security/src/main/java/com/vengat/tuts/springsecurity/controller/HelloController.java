package com.vengat.tuts.springsecurity.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/helloSimple")
	public String helloSimple() {
	
		return "Hello";
	}


	@GetMapping("/hello")
	public String hello() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication a = context.getAuthentication();

		return "Hello, " + a.getName() + "!";
	}
	
	
	/**
	 * Obtaining the authentication from the context is even more comfortable at the endpoint level, as Spring 
	 * knows to inject it directly into the method parameters. You don’t need to refer every time to the SecurityContextHolder class 
	 * explicitly
	 * @param authentication
	 * @return
	 */
	@GetMapping("/helloAlternative")
	public String helloAlternative(Authentication authentication) {
		return "Hello, " + authentication.getName() + "!";
	}

	/**
	 * curl -u user:99ff79e3-8ca0-401c-a396-0a8625ab3bad http://localhost:8080/hello
		Hello, user!
	 */
	
	@GetMapping("/bye")
	@Async
	public void goodbye() {
		SecurityContext context = SecurityContextHolder.getContext();
		String username = context.getAuthentication().getName();
	}
	
	/**
	 * When dealing with threads that our code starts without letting the framework know about them, 
	 * we have to manage propagation of the details from the security context to the next thread. 
	 * In section 5.2.4, you applied a technique to copy the details from the security context by making use 
	 * of the task itself. Spring Security provides some great utility 
	 * classes like DelegatingSecurityContextRunnable and DelegatingSecurityContextCallable
	 * @return
	 * @throws Exception
	 */
	
	
	@GetMapping("/ciao")
	public String ciao() throws Exception {
		
		Callable<String> task = () -> {
			SecurityContext context = SecurityContextHolder.getContext();
			return context.getAuthentication().getName();
		};
		
		ExecutorService 	 e = Executors.newCachedThreadPool();
		
		try {
			var contextTask = new DelegatingSecurityContextCallable<>(task);
			return "Ciao, " + e.submit(task).get() + "!";
		} finally {
			e.shutdown();
		}
				
	}
	
	
	/**
	 * An alternative to decorating tasks is to use a particular type of Executor. 
	 * In the next example, you can observe that the task remains a simple Callable<T>, but the t
	 * hread still manages the security context. The propagation of the security context happens because 
	 * an implementation called DelegatingSecurityContextExecutorService 
	 * decorates the ExecutorService. The DelegatingSecurityContext-ExecutorService
	 * @return
	 * @throws Exception
	 */
	
	@GetMapping("/hola")
	public String hola() throws Exception {
		
		Callable<String> task = () -> {
			SecurityContext context = SecurityContextHolder.getContext();
			return context.getAuthentication().getName();
		};
		
		ExecutorService e = Executors.newCachedThreadPool();
		
		e = new DelegatingSecurityContextExecutorService(e);
		
		try {
			return "Hola ,"+ e.submit(task).get() + "!";
		} finally {
			e.shutdown();
		}
		
	}
	
	


}
