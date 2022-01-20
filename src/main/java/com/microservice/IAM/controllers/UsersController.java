package com.microservice.IAM.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.IAM.models.Role;
import com.microservice.IAM.models.User;
import com.microservice.IAM.repository.RolesRepo;
import com.microservice.IAM.repository.UsersRepo;

@RestController
@CrossOrigin
public class UsersController {
	
	@Autowired
	UsersRepo repo;
	
	@Autowired
	RolesRepo rolesRepo;
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> GetUser(@PathVariable Long userId) {
		Optional<User> user = repo.findById(userId);
		if(user.isPresent()){
			return ResponseEntity.ok(user.get());
		}
		return ResponseEntity.ok("No user found with this Id");
	}
	
	@DeleteMapping("/user/{userId}")
	public  ResponseEntity<?> DeleteUser(@PathVariable Long userId) {
		Optional<User> user = repo.findById(userId);
		if(user.isPresent()){
			repo.deleteById(userId);
			return ResponseEntity.ok("User Deleted Successfully");
		}
		return ResponseEntity.ok("No user found with this Id");
	}
	
	@PutMapping("/user/{userId}/addRole")
	public ResponseEntity<?> UpdateUserRole(@PathVariable Long userId, @RequestParam Long roleId) {
		Optional<User> user1 = repo.findById(userId);
		if(user1.isEmpty()){
			return ResponseEntity.ok("No user found with this Id");
		}
		Optional<Role> role1 = rolesRepo.findById(roleId);
		if(role1.isEmpty()) {
			return ResponseEntity.ok("No role found with this Id");
		}
		User user = user1.get();
		Role role = role1.get();
		user.AddRole(role);
		return ResponseEntity.ok(repo.save(user));
	}
	
	@PutMapping("/user/{userId}/deleteRole")
	public ResponseEntity<?> DeleteUserRole(@PathVariable Long userId, @RequestParam Long roleId) {
		Optional<User> user1 = repo.findById(userId);
		if(user1.isEmpty()){
			return ResponseEntity.ok("No user found with this Id");
		}
		Optional<Role> role1 = rolesRepo.findById(roleId);
		if(role1.isEmpty()) {
			return ResponseEntity.ok("No role found with this Id");
		}
		User user = user1.get();
		Role role = role1.get();
		user.DeleteRole(role);
		return ResponseEntity.ok(repo.save(user));
	}
	

}
