package com.microservice.IAM.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.IAM.models.Role;
import com.microservice.IAM.repository.RolesRepo;

@RestController
@CrossOrigin
public class RolesController {
	
	@Autowired
	RolesRepo repo;
	
	@PostMapping("/addRole")
	public ResponseEntity<?> AddRole(@RequestParam String roleName){
		List<Role> roles = repo.findByName(roleName);
		if(roles.isEmpty()) {
			Role role = repo.save(new Role(roleName));
			return ResponseEntity.ok(role);
		}
		return ResponseEntity.ok("Role Already Exist With this Name");
	}
	
	@GetMapping("/getRoles")
	public ResponseEntity<?> GetRoles() {
		List<Role> roles = repo.findAll();
		return ResponseEntity.ok(roles);
		
	}
	
	@PutMapping("/updateRoleName/{roleId}")
	public ResponseEntity<?> updateRoleName(@PathVariable("roleId") Long roleId , @RequestParam String roleName) {
		
		Optional<Role> role1 = repo.findById(roleId);
		if(role1.isEmpty()){
			return ResponseEntity.ok("Role not found with this Id");
		}
		Role role = role1.get();
		role.setName(roleName);
		Role updatedRole = repo.save(role);
		return ResponseEntity.ok(updatedRole);
	}
	
	@DeleteMapping("/deleteRole/{roleId}")
	public ResponseEntity<?> DeleteRole(@PathVariable("roleId") Long roleId) {
		Optional<Role> role1 = repo.findById(roleId);
		if(role1.isEmpty()){
			return ResponseEntity.ok("Role not found with this Id");
		}
		repo.deleteById(roleId);
		return ResponseEntity.ok("role deleted Successfully");
	}
	
}
