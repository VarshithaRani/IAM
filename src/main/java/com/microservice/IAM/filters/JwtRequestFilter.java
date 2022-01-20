package com.microservice.IAM.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.microservice.IAM.service.MyUserDetailsService;
import com.microservice.IAM.utils.jwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private jwtUtil Jwtutil;

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");
		
		String username = null;
		String token = null;
		 if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            token = authorizationHeader.substring(7);
	            username = Jwtutil.extractUsername(token);
		 
		 if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			 	UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
			 	if(Jwtutil.validateToken(token, userDetails, request)) {
			 		
			 		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
		 					new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			 		usernamePasswordAuthenticationToken
             		.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			 		
			 		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			 		
			 	}
		 	}
		}
	filterChain.doFilter(request, response);
	}
}
