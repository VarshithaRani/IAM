package com.microservice.IAM.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservice.IAM.models.Role;
import com.microservice.IAM.models.User;
import com.microservice.IAM.repository.RolesRepo;
import com.microservice.IAM.repository.UsersRepo;
import com.microservice.IAM.requestModels.AuthenticationRequest;
import com.microservice.IAM.utils.MyUserDetails;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsersRepo repo;
	
	@Autowired
	private RolesRepo rolesRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repo.findByUsername(username);
		if (user.isEmpty()) {
	            throw new UsernameNotFoundException("Could not find user");
	        }
		return new MyUserDetails(user.get());
	}

	public User createUser(AuthenticationRequest request) {
		Optional<User> userExisting = repo.findByUsername(request.getUsername());
		if(userExisting.isPresent()) {
			return null;
		}
		Set<Role> roles = new HashSet<Role>();
		for (Long Id : request.getRoles()) {
			Optional<Role> role = rolesRepo.findById(Id);
			if(role.isPresent()) {
				roles.add(role.get());
			}
        }
		User user = new User(request.getFirstName(),request.getLastName(),request.getEmail(),request.getUsername(), request.getPassword(), roles);
		return repo.save(user);
	}
}
