package com.system.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.project.entities.Course;
import com.system.project.repository.CourseRepository;
import com.system.project.repository.FeesRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    private FeesRepository feesRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(null);
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }
    public List<Course> getAllCoursesWithClasses() {
        return courseRepository.findAll();
    }

    public void deleteCourse(Long courseId) throws ClassNotFoundException {
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
        } else {
            throw new ClassNotFoundException("Course with ID " + courseId + " not found.");
        }
    }

    public void deleteCourseWithDependencies(Long courseId) {
        // Check for dependencies in the fees table
        if (feesRepository.existsByCourseId(courseId)) {
            // Option 1: Remove dependent fees before deleting the course
            feesRepository.deleteById(courseId);
        }

        // Delete the course
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
        } else {
            throw new IllegalStateException("Course with ID " + courseId + " does not exist.");
        }
    }

}
