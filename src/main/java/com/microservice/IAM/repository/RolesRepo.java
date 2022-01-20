package com.microservice.IAM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.IAM.models.Role;

@Repository
public interface RolesRepo extends JpaRepository<Role, Long> {

	public List<Role> findByName(String name);

}
