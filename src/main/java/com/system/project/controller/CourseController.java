/*package com.system.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.project.entities.Course;
import com.system.project.entities.CourseDto;
import com.system.project.service.CourseService;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String showAdminPage(Model model) {
    	model.addAttribute("courseDto", new CourseDto());
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "admin";
    }

    @PostMapping("/addCourse")
    public String addCourse(@Valid @ModelAttribute("courseDto") CourseDto courseDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Course> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses);
            return "admin";
        }

        Course course = new Course();
        course.setCourseName(courseDto.getCourseName());
        course.setClassName(courseDto.getClassName());
        course.setCasteName(courseDto.getCasteName());
        course.setAdmnProcessing(courseDto.getAdmnProcessing());
        course.setCollegeMagazine(courseDto.getCollegeMagazine());
        course.setCulFees(courseDto.getCulFees());
        course.setGymkhanaFees(courseDto.getGymkhanaFees());
        course.setIdlibCard(courseDto.getIdlibCard());
        course.setLaboratoryFees(courseDto.getLaboratoryFees());
        course.setLibraryFees(courseDto.getLibraryFees());
        course.setNss(courseDto.getNss());
        course.setSport(courseDto.getSport());
        course.setTutionFees(courseDto.getTutionFees());
        course.setUtility(courseDto.getUtility());
        course.setLabFees(courseDto.getLabFees());
        course.setOtherFees(courseDto.getOtherFees());
        course.setTotalFees(courseDto.getTotalFees());

        courseService.saveCourse(course);
        return "redirect:/admin";
    }

    @GetMapping("/editcourse")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        try {
            Course course = courseService.getCourseById(id);
            if (course == null) {
                throw new IllegalArgumentException("Invalid course Id:" + id);
            }
            model.addAttribute("course", course);
            return "editcourse";  // This should match the name of your Thymeleaf template
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/edit")
    public String editCourse(@ModelAttribute Course course) {
        courseService.saveCourse(course);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String deleteCourse(@RequestParam Long id) {
        try {
            courseService.deleteCourse(id);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/admin";
    }
}*/