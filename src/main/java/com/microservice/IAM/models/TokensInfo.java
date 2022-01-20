package com.microservice.IAM.models;

import java.io.Serializable;

public class TokensInfo implements Serializable {
	
    private String token;
	private Boolean isvalid;
	
	public TokensInfo() {
		super();
	}
	
	public TokensInfo(String token, Boolean isvalid) {
		super();
		this.token = token;
		this.isvalid = isvalid;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Boolean getIsvalid() {
		return this.isvalid;
	}
	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}
	
}
