package com.system.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.project.entities.Class;
import com.system.project.entities.Course;
import com.system.project.repository.ClassRepository;
import com.system.project.repository.CourseRepository;

import jakarta.transaction.Transactional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Fetch all classes
    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    public Class getClassById(Long id) {
        return classRepository.findById(id).orElse(null);
    }

    // Add this method to save a class
    public void saveClass(Class classEntity) {
        classRepository.save(classEntity);
    }

    public void addClass(String className, Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            Class classEntity = new Class();
            classEntity.setClassName(className);
            classEntity.setCourse(course);
            classRepository.save(classEntity);
        } else {
            // Handle course not found scenario (e.g., throw an exception, log an error)
            System.err.println("Course with ID " + courseId + " not found.");
        }
    }

    @Transactional
    public List<Class> findByCourseId(Long courseId) {
        return classRepository.findByCourseId(courseId);
    }

    public void deleteClassesByCourseId(Long courseId) {
        classRepository.deleteByCourseId(courseId);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    public boolean existsByClassNameAndCourseId(String className, Long courseId) {
        return classRepository.existsByClassNameAndCourseId(className, courseId);
    }
}