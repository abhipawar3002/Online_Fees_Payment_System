package com.system.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.project.entities.StdLogin;

public interface StdLoginRepository extends JpaRepository<StdLogin, Long>{
	 StdLogin findByEmail(String email);

}
