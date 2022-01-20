package com.microservice.IAM.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.IAM.models.User;

@Repository
public interface UsersRepo extends JpaRepository<User, Long> {
	
	public Optional<User> findByUsername(String username);

}