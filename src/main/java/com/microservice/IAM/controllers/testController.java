package com.microservice.IAM.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class testController {

	@GetMapping("/helloWorld")
	public ResponseEntity<?> helloWorld(){
		return ResponseEntity.ok("hello World...Authentication Success");
	}
	
	@GetMapping("/student/test")
	public ResponseEntity<?> checkStudentAccess(){
		return ResponseEntity.ok("Student Access...AuthZ: student or Invigilator");
	}
	
	@GetMapping("/invigilator/test")
	public ResponseEntity<?> checkInvigilatorAccess(){
		return ResponseEntity.ok("Invigilator Access...AuthZ: Invigilator");
	}
	
	/*
	@GetMapping("/")
	public ResponseEntity<?> hello(@RequestBody AuthenticationRequest request, HttpServletRequest clientRequest) {
		String authorizationHeader = clientRequest.getHeader("Authorization");
		String token = null;
		 if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            token = authorizationHeader.substring(7);
		 }
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
	 	if(Jwtutil.validateToken(token, userDetails, clientRequest))
	 	{
	 		JSONObject data = new JSONObject();
	 		data.put("data", "token valid logged in ");
	 		return ResponseEntity.ok(data);
	 	}
	 	else {
	 		JSONObject data = new JSONObject();
	 		data.put("data", "token logged out");
	 		return ResponseEntity.ok(data);
	 	}
	} */
	
	
}
