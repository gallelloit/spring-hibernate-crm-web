package com.gallelloit.springdemo.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * 
 * Spring Security needs this class extending from `AbstractSecurityWebApplicationInitializer` to:
 * 
 * * Automatically register the springSecurityFilterChain Filter for every URL in your application
 * * Add a ContextLoaderListener that loads the WebSecurityConfig.
 * 
 * @author pgallello
 *
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}
