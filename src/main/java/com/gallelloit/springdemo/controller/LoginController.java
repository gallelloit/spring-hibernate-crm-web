package com.gallelloit.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * Spring Security requirement to customize Login and Access Denied pages
 * The url of the showMyLoginPage and the name of the access-denied jpg are both
 * configured in `DemoSecurityConfig.configure(HttpSecurity)`  
 * 
 * @author pgallello
 *
 */
@Controller
public class LoginController {

	@GetMapping("/showLogin")
	public String showLogin () {
		
		return "login";
		
	}

	
	// Access denied mapping
	@GetMapping("/access-denied")
	public String showMyAccessDeniedPage () {
		
		return "access-denied";
		
	}
}
