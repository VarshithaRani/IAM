package com.microservice.IAM.utils;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.microservice.IAM.models.TokensInfo;
import com.microservice.IAM.repository.TokensRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class jwtUtil {
	
	private String SECRET_KEY = "proctoring";
	private int EXPIRATION_TIME = 1000 * 60 * 60 * 10;

	
	@Autowired
	TokensRepo repo;
	
	public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }
    
    public String extractIp(String token) {
        return extractClaim(token, claims -> (String) claims.get("Ip"));
    }
    
    public Collection<? extends GrantedAuthority>  extractRoles(String token) {
        return extractClaim(token, claims -> (List) claims.get("ROLES"));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
    private Claims extractAllClaims(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	    }

    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }
	
	public String generateToken(UserDetails userdetails,HttpServletRequest request) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("ROLES", userdetails.getAuthorities()); 
		claims.put("Ip",getClientIp(request));
		return createToken(claims,userdetails.getUsername());
	}
	
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	 public Boolean validateToken(String token, UserDetails userDetails,HttpServletRequest request ) {
	        final String username = extractUsername(token);
	        final String ipAddress = getClientIp(request);
	        if(username.equals(userDetails.getUsername()) && !isTokenExpired(token) && ipAddress.equals(extractIp(token))){
	        	TokensInfo tokenInfo = repo.getTokenInfo(token);
	        	if(tokenInfo != null) {
	        		if(tokenInfo.getIsvalid()){
	        			System.out.println(tokenInfo.getIsvalid());
		        		return true;
		        	}
	        	}
	        	else {
	        		return true;
	        	}
	        }
	        return false;
	}
	
	@CachePut(key = "#token", value = "tokenCache")
	public Boolean InvalidateToken(String token) {
		TokensInfo tokenInfo = new TokensInfo(token,false);
		repo.addTokenInfo(tokenInfo);
		return true;
	}

	 
	private static String getClientIp(HttpServletRequest request) {
	        String remoteAddr = "";
	        if (request != null) {
	            remoteAddr = request.getHeader("X-FORWARDED-FOR");
	            if (remoteAddr == null || "".equals(remoteAddr)) {
	                remoteAddr = request.getRemoteAddr();
	            }
	        }
	        return remoteAddr;
	 }
}
