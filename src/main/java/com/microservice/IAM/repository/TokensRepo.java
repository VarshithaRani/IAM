package com.microservice.IAM.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.microservice.IAM.models.TokensInfo;

@Repository
public class TokensRepo {

    public static final String KEY = "tokenCache";
    @Autowired
    private RedisTemplate<String, TokensInfo> redisTemplate;

    public TokensRepo(RedisTemplate<String, TokensInfo> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    public TokensRepo() {

    }
    
    public TokensInfo getTokenInfo(String token){
        return (TokensInfo) this.redisTemplate.opsForHash().get(KEY,token);
    }

    public void addTokenInfo(TokensInfo tokenInfo){
        this.redisTemplate.opsForHash().put(KEY,tokenInfo.getToken(),tokenInfo);
    }

    public void deleteTokenInfo(String token){
    	this.redisTemplate.opsForHash().delete(KEY,token);
    }

    public void updateTokenInfo(TokensInfo tokenInfo){
        addTokenInfo(tokenInfo);
    }
}
