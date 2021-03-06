package com.microservice.IAM.requestModels;

import java.util.List;

public class AuthenticationRequest {
	
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private List<Long> roles;
	
	public AuthenticationRequest() {
		super();
	}
	public AuthenticationRequest(String userName, String password) {
		super();
		this.username = userName;
		this.password = password;
	}
	
	public AuthenticationRequest(String firstName, String lastName, String username, String password, String email,
			List<Long> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Long> getRoles() {
		return roles;
	}
	public void setRoles(List<Long> roles) {
		this.roles = roles;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return this.username+"  "+this.password+" "+this.roles;
	}
	
}
