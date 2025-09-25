package com.system.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.system.project.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	 @Query("SELECT c FROM Course c JOIN FETCH c.classes")
	    List<Course> findAllCourses();

	void deleteById(Integer id);
}