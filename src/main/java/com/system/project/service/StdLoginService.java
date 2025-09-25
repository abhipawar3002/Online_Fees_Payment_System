package com.system.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.project.entities.StdLogin;
import com.system.project.repository.StdLoginRepository;

@Service
public class StdLoginService {

	@Autowired
	private StdLoginRepository stdLoginRepository;

	// Save or update a student
    public StdLogin saveOrUpdateStudent(StdLogin stdLogin) {
        return stdLoginRepository.save(stdLogin);
    }

    // Get all students
    public List<StdLogin> getAllStudents() {
        return stdLoginRepository.findAll();
    }

    // Get a student by ID
    public Optional<StdLogin> getStudentById(Long id) {
        return stdLoginRepository.findById(id);
    }

    // Delete a student by ID
    public void deleteStudent(Long id) {
        stdLoginRepository.deleteById(id);
    }

    // Find a student by email
    public StdLogin getStudentByEmail(String email) {
        return stdLoginRepository.findByEmail(email);
    }


}
