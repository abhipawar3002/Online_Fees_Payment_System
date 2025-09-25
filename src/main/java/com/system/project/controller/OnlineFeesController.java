package com.system.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.project.entities.StdLogin;
import com.system.project.service.StdLoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class OnlineFeesController {

    @Autowired
    private StdLoginService stdLoginService;

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @PostMapping("/adminlogin")
    public String adminLogin(@RequestParam String username, @RequestParam String password, Model model) {
        if ("admin".equals(username) && "pass123".equals(password)) {
            model.addAttribute("message", "Login successful!");
            return "admin/dashboard"; // Ensure this file exists in the correct directory
        }
        model.addAttribute("message", "Invalid username or password.");
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalidate the session
        }
        return "redirect:/login"; // Redirect to the login page
    }

    @PostMapping("/studentlogin")
    public ResponseEntity<?> studentLogin(@RequestBody Map<String, String> credentials, HttpSession session) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        try {
            StdLogin student = stdLoginService.getStudentByEmail(email);
            if (student == null || !student.getPassword().equals(password)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid email or password."));
            }

            // Store the student's email in the session
            session.setAttribute("studentEmail", email);

            return ResponseEntity.ok(Map.of("message", "Login successful!", "redirectUrl", "/student/dashboard"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An error occurred. Please try again."));
        }
    }

    @PostMapping("/studentsignup")
    public ResponseEntity<?> studentSignUp(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String email = credentials.get("email");
        String password = credentials.get("password");

        try {
            StdLogin newStudent = new StdLogin();
            newStudent.setUserName(username);
            newStudent.setEmail(email);
            newStudent.setPassword(password);

            StdLogin savedStudent = stdLoginService.saveOrUpdateStudent(newStudent);
            if (savedStudent != null) {
                return ResponseEntity.ok(Map.of("message", "Sign up successful! Please login."));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Sign up failed. Please try again."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An error occurred. Please try again."));
        }
    }
}