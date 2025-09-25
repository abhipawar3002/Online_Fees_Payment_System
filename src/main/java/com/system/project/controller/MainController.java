/*package com.system.project.controller;

import com.system.project.entities.Cast;
import com.system.project.service.CastService;
import com.system.project.service.ClassService;
import com.system.project.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassService classService;

    @Autowired
    private CastService castService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "index";
    }

    @GetMapping("/classes")
    public String getClasses(@RequestParam Long courseId, Model model) {
        model.addAttribute("classes", classService.getClassesByCourseId(courseId));
        return "classDropdown";
    }

    @GetMapping("/casts")
    public String getCasts(@RequestParam Long classId, Model model) {
        model.addAttribute("casts", castService.getCastsByClassId(classId));
        return "castDropdown";
    }

    @GetMapping("/fees")
    public String getFees(@RequestParam Long castId, Model model) {
        Cast cast = castService.getCastById(castId);
        model.addAttribute("cast", cast);
        return "fees";
    }
}*/