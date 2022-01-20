package com.microservice.IAM.controllers;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

import com.microservice.IAM.models.User;
import com.microservice.IAM.requestModels.AuthenticationRequest;
import com.microservice.IAM.responseModels.AuthenticationResponse;
import com.microservice.IAM.service.MyUserDetailsService;
import com.microservice.IAM.utils.jwtUtil;

@RestController
@CrossOrigin
public class AuthenticationController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	jwtUtil Jwtutil;
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@PostMapping("/register")
	public ResponseEntity<?> UserRegistration(@RequestBody AuthenticationRequest request){
		User user = myUserDetailsService.createUser(request);
		if(user==null) {
			return ResponseEntity.ok("User already Exist with this username.");
		}
 		return new ResponseEntity<User>(user,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> AuthenticateUser(@RequestBody AuthenticationRequest request, HttpServletRequest clientRequest) throws Exception{
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		}
		catch (Exception exception) {
			throw new Exception("inavalid username/password");
		}
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
		final String jwt = Jwtutil.generateToken(userDetails,clientRequest);
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(jwt), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> checkJWTToken(HttpServletRequest request){
		String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String token = null;
		 if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            token = authorizationHeader.substring(7);
	            username = Jwtutil.extractUsername(token);
		 
		 if(username != null) {
			 	UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
			 	if(Jwtutil.validateToken(token, userDetails, request)) {
			 		JSONObject data = new JSONObject();
			 		data.put("data", "valid");
			 		return ResponseEntity.ok(data);
			 	}
		 	}
		}
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid/Expired Token");
	}
	
	@PostMapping("/userLogout")
	public ResponseEntity<?> logout(HttpServletRequest request){
		String authorizationHeader = request.getHeader("Authorization");
		String token = null;
		 if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            token = authorizationHeader.substring(7);
	            Jwtutil.InvalidateToken(token);
		 }
		return ResponseEntity.ok("log out successfully");
	}
	
}
