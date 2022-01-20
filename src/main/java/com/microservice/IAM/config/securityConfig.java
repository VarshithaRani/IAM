package com.microservice.IAM.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.microservice.IAM.filters.JwtRequestFilter;
import com.microservice.IAM.service.MyUserDetailsService;
import com.sun.nio.file.SensitivityWatchEventModifier;

@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter{
	
	 private static final String[] AUTH_WHITELIST = {
	            // -- swagger ui
			    "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**",
	            // other public endpoints of your API may be appended to this array
			    "/register",
	            "/login",
	            "/authenticate"
	    };
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(AUTH_WHITELIST).permitAll()
        .antMatchers("/student/**").hasAnyAuthority("STUDENT", "INVIGILATOR")
        .antMatchers("/invigilator/**").hasAuthority("INVIGILATOR")
		.anyRequest().authenticated()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
