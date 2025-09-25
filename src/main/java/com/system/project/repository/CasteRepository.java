package com.system.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.system.project.entities.Caste;


public interface CasteRepository extends JpaRepository<Caste, Long> {
	@Query("SELECT c FROM Caste c " +
		       "JOIN FETCH c.fees f " +
		       "JOIN FETCH f.classEntity " +
		       "WHERE c.id = :casteId")
		List<Caste>findClassByCasteId(@Param("casteId") Long casteId);


}