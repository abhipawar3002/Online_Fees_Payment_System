package com.system.project.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.project.entities.Caste;
import com.system.project.entities.Class;
import com.system.project.entities.StdLogin;
import com.system.project.service.CasteService;
import com.system.project.service.ClassService;
import com.system.project.service.CourseService;
import com.system.project.service.FeePaymentService;
import com.system.project.service.FeesService;
import com.system.project.service.StdLoginService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ClassService classService;
    @Autowired
    private CasteService casteService;
    @Autowired
    private FeesService feesService;
    @Autowired
    private StdLoginService stdLoginService;
    @Autowired
    private FeePaymentService feePaymentService;


    @GetMapping("/dashboard")
    public String studentDashboard(Model model, HttpSession session) {
        // Retrieve the logged-in student's email from the session
        String studentEmail = (String) session.getAttribute("studentEmail");

        if (studentEmail == null) {
            // Redirect to login if the student is not logged in
            return "redirect:/index";
        }

        // Fetch the student details from the database
        StdLogin student = stdLoginService.getStudentByEmail(studentEmail);

        if (student == null) {
            // Handle case where student is not found
            return "redirect:/index";
        }

        // Add the student object to the model
        model.addAttribute("student", student);

        // Set cache-control headers to prevent caching
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        headers.set("Pragma", "no-cache"); // HTTP 1.0
        headers.set("Expires", "0"); // Proxies

        // Return the view name
        return "student/dashboard";
    }

    @PostMapping("/logout") // Handle POST requests for logout
    public String logout(HttpSession session) {
        // Invalidate the session to log out the user
        session.invalidate();
        // Redirect to the home page or login page
        return "redirect:/index";
    }


    @GetMapping("/payfees")
    public String showPayFeesPage(Model model, Principal principal, HttpSession session) {
        // Get current authenticated student

        String studentEmail = (String) session.getAttribute("studentEmail");
        if (studentEmail == null) {

    	}
		StdLogin student = stdLoginService.getStudentByEmail(studentEmail);


        model.addAttribute("student", student);
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("castes", casteService.getAllCastes());

        return "student/payfees";
    }

    @GetMapping("/classes")
    @ResponseBody
    public List<Class> getClassesByCourseId(@RequestParam Long courseId) {
        return classService.findByCourseId(courseId);
    }

    @GetMapping("/castes")
    @ResponseBody
    public List<Caste> getCastesByClassId(@RequestParam Long classId) {
        return casteService.getCastesByClassId(classId);
    }

    @GetMapping("/fees")
    @ResponseBody
    public Map<String, Double> getFees(@RequestParam Long courseId, @RequestParam Long classId, @RequestParam Long casteId) {
        // Fetch fees from the service
        double tuitionFees = feesService.getTuitionFees(courseId, classId, casteId);
        double libraryFees = feesService.getLibraryFees(courseId, classId, casteId);
        double gymkhanaFees = feesService.getGymkhanaFees(courseId, classId, casteId);
        double laboratoryFees = feesService.getLibraryFees(courseId, classId, casteId);
        double adminProcessingFees = feesService.getAdminProcessingFees(courseId, classId, casteId);
        double studentWelfareFund = feesService.getStudentWelfareFund(courseId, classId, casteId);
        double utilityFees = feesService.getUtilityFees(courseId, classId, casteId);
        double idLibCardFees = feesService.getIdLibCardFees(courseId, classId, casteId);
        double eCharges = feesService.getECharges(courseId, classId, casteId);
        double vcFund = feesService.getVcFund(courseId, classId, casteId);
        double nssFees = feesService.getNssFees(courseId, classId, casteId);
        double sportsFees = feesService.getSportsFees(courseId, classId, casteId);
        double collegeCulFees = feesService.getCollegeCulFees(courseId, classId, casteId);
        double otherFees = feesService.getOtherFees(courseId, classId, casteId);

        // Create a map to hold all fee components
        Map<String, Double> feesMap = new HashMap<>();
        feesMap.put("tuitionFees", tuitionFees);
        feesMap.put("libraryFees", libraryFees);
        feesMap.put("gymkhanaFees", gymkhanaFees);
        feesMap.put("laboratoryFees", laboratoryFees);
        feesMap.put("adminProcessingFees", adminProcessingFees);
        feesMap.put("studentWelfareFund", studentWelfareFund);
        feesMap.put("utilityFees", utilityFees);
        feesMap.put("idLibCardFees", idLibCardFees);
        feesMap.put("eCharges", eCharges);
        feesMap.put("vcFund", vcFund);
        feesMap.put("nssFees", nssFees);
        feesMap.put("sportsFees", sportsFees);
        feesMap.put("collegeCulFees", collegeCulFees);
        feesMap.put("otherFees", otherFees);

        return feesMap;
    }

    @GetMapping("/stdpayoption")
    public String showPaymentOptions(Model model, Principal principal , HttpSession session) {
    	 String studentEmail = (String) session.getAttribute("studentEmail");
         if (studentEmail == null) {

     	}
         StdLogin student = stdLoginService.getStudentByEmail(studentEmail);
        if (student == null) {
            // Handle case where student doesn't exist
            return "redirect:/error";
        }

        Double totalAmount = feesService.calculateTotalFees(student.getId());
        model.addAttribute("student", student);
        model.addAttribute("totalAmount", totalAmount != null ? totalAmount : 0.0);

        return "student/stdpayoption";
    }

}