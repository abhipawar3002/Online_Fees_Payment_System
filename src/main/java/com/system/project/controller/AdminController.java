package com.system.project.controller;

import java.awt.print.Pageable;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.system.project.entities.Caste;
import com.system.project.entities.Class;
import com.system.project.entities.Course;
import com.system.project.entities.Fees;
import com.system.project.service.CasteService;
import com.system.project.service.ClassService;
import com.system.project.service.CourseService;
import com.system.project.service.FeesService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController<Payment> {

    private static final int PAGE_SIZE = 0;

	@Autowired
    private CourseService courseService;

    @Autowired
    private ClassService classService;

    @Autowired
    private CasteService casteService;

    @Autowired
    private FeesService feesService;
    
    
    
   

    // Dashboard - Display all entities
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("course", new Course());
        return "admin/dashboard";
    }

    @PostMapping("/logout") // Handle POST requests for logout
    public String logout(HttpSession session) {
        // Invalidate the session to log out the user
        session.invalidate();
        // Redirect to the home page or login page
        return "redirect:/home";
    }

    // Add Course - Display forma
    @GetMapping("/addCourse")
    public String showAddCoursePage(Model model) {
        // Add a new Course object to the model for the form
        model.addAttribute("course", new Course());

        // Fetch the list of existing courses
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);

        return "admin/addCourse";
    }

    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute Course course, Model model, RedirectAttributes redirectAttributes) {
        try {
            courseService.saveCourse(course);
            redirectAttributes.addFlashAttribute("message", "Course added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error adding course: " + e.getMessage());
        }

        // Redirect to the addCourse page to avoid form resubmission
        return "redirect:/admin/addCourse";
    }

    // Delete Course - Handle delete request
    @PostMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Course course = courseService.getCourseById(id);
            if (course == null) {
                redirectAttributes.addFlashAttribute("error", "Course not found!");
                return "redirect:/admin/addCourse";
            }

            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("message", "Course deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting course: " + e.getMessage());
        }
        return "redirect:/admin/addCourse";
    }

    // Add Class - Display form
    @GetMapping("/addClass")
    public String showAddClassForm(Model model) {
        model.addAttribute("class", new Class());
        model.addAttribute("courses", courseService.getAllCoursesWithClasses());
        List<Class> classes = classService.getAllClasses();
        model.addAttribute("classes", classes);
        return "admin/addClass";
    }

    // Add a new class
    @PostMapping("/addClass")
    public String addClass(@RequestParam String className,
                           @RequestParam Long courseId,
                           RedirectAttributes redirectAttributes) {
        // Check if the class already exists for the given course
        if (classService.existsByClassNameAndCourseId(className, courseId)) {
            // Add an error message to be displayed on the form page
            redirectAttributes.addFlashAttribute("error", "This class already exists for the selected course.");
        } else {
            // Save the class if it doesn't exist
            classService.addClass(className, courseId);
            redirectAttributes.addFlashAttribute("success", "Class added successfully!");
        }
        return "redirect:/admin/addClass"; // Redirect back to the form page
    }

    // Delete class
    @PostMapping("/deleteClass/{id}")
    public String deleteClass(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            classService.deleteClass(id);
            redirectAttributes.addFlashAttribute("delete", "Class deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting class: " + e.getMessage());
        }
        return "redirect:/admin/addClass";
    }

    // Add Fees - Display form
    @GetMapping("/addFees")
    public String addFeesForm(Model model) {
        List<Fees> feesList = feesService.getAllFees();
        model.addAttribute("feesList", feesList);
        model.addAttribute("courses", courseService.getAllCoursesWithClasses());
        model.addAttribute("classes", classService.getAllClasses());
        model.addAttribute("castes", casteService.getAllCastes());
        return "admin/addFees";
    }

    // Get classes by course ID
    @GetMapping("/classes")
    @ResponseBody
    public List<Class> getClassesByCourseId(@RequestParam Long courseId) {
        return classService.findByCourseId(courseId);
    }

    // Add Fees - Process form submission
    @PostMapping("/addfees")
    public String addFees(@ModelAttribute Fees fees,
                          @RequestParam Long courseId,
                          @RequestParam Long classId,
                          @RequestParam Long casteId,
                          @RequestParam Double tuitionFees,
                          @RequestParam Double libraryFees,
                          @RequestParam Double gymkhanaFees,
                          @RequestParam Double laboratoryFees,
                          @RequestParam Double adminProcessingFees,
                          @RequestParam Double studentWelfareFund,
                          @RequestParam Double utilityFees,
                          @RequestParam Double idLibCardFees,
                          @RequestParam Double eCharges,
                          @RequestParam Double vcFund,
                          @RequestParam Double nssFees,
                          @RequestParam Double sportsFees,
                          @RequestParam Double collegeCulFees,
                          @RequestParam Double otherFees,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        try {
            // Check if the combination of course, class, and caste already exists
            if (feesService.isDuplicateFees(courseId, classId, casteId)) {
                redirectAttributes.addFlashAttribute("error", "Fees for this course, class, and caste combination already exist.");
                return "redirect:/admin/addFees";
            }

            // Fetch course, class, and caste
            Course course = courseService.getCourseById(courseId);
            Class classEntity = classService.getClassById(classId);
            Caste caste = casteService.getCasteById(casteId);

            // Validate fetched entities
            if (course == null || classEntity == null || caste == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid course, class, or caste selected.");
                return "redirect:/admin/addFees";
            }

            // Set the course, class, and caste for the fees
            fees.setCourse(course);
            fees.setClassEntity(classEntity);
            fees.setCaste(caste);

            // Set individual fee fields
            fees.setTuitionFees(tuitionFees);
            fees.setLibraryFees(libraryFees);
            fees.setGymkhanaFees(gymkhanaFees);
            fees.setLaboratoryFees(laboratoryFees);
            fees.setAdminProcessingFees(adminProcessingFees);
            fees.setStudentWelfareFund(studentWelfareFund);
            fees.setUtilityFees(utilityFees);
            fees.setIdLibCardFees(idLibCardFees);
            fees.seteCharges(eCharges);
            fees.setVcFund(vcFund);
            fees.setNssFees(nssFees);
            fees.setSportsFees(sportsFees);
            fees.setCollegeCulFees(collegeCulFees);
            fees.setOtherFees(otherFees);

            // Calculate total amount
            double totalAmount = tuitionFees + libraryFees + gymkhanaFees + laboratoryFees +
                                 adminProcessingFees + studentWelfareFund + utilityFees +
                                 idLibCardFees + eCharges + vcFund + nssFees + sportsFees +
                                 collegeCulFees + otherFees;
            fees.setAmount(totalAmount);

            // Save the fees
            feesService.saveFees(fees);
            redirectAttributes.addFlashAttribute("message", "Fees added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding fees: " + e.getMessage());
        }

        // Fetch the updated list of fees
        List<Fees> feesList = feesService.getAllFees();
        model.addAttribute("feesList", feesList);

        // Re-populate the model attributes
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("classes", classService.getAllClasses());
        model.addAttribute("castes", casteService.getAllCastes());

        return "admin/addFees";
    }

    // Edit Fees - Process form submission
    @PostMapping("/editfees")
    @ResponseBody
    public String editFees(@RequestBody Fees fees) {
        try {
            Course course = courseService.getCourseById(fees.getCourse().getId());
            Class classEntity = classService.getClassById(fees.getClassEntity().getId());
            Caste caste = casteService.getCasteById(fees.getCaste().getId());

            fees.setCourse(course);
            fees.setClassEntity(classEntity);
            fees.setCaste(caste);

            feesService.saveFees(fees);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    // Delete Fees - Process delete request
    @DeleteMapping("/deletefees/{id}")
    @ResponseBody
    public String deleteFees(@PathVariable Long id) {
        try {
            feesService.deleteFees(id);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
    
    @GetMapping("/paymenthistory")
    public String home(Model model) {
        return "admin/paymenthistory";
    }

}