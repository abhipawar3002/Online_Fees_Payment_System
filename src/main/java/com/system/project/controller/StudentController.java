package com.system.project.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.system.project.entities.Caste;
import com.system.project.entities.Class;
import com.system.project.entities.FeePayment;
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
    
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;
    
    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;


    @GetMapping("/dashboard")
    public String studentDashboard(Model model, HttpSession session) {
        // Retrieve the logged-in student's email from the session
        String studentEmail = (String) session.getAttribute("studentEmail");

        if (studentEmail == null) {
            // Redirect to login if the student is not logged in
            return "redirect:/home";
        }

        // Fetch the student details from the database
        StdLogin student = stdLoginService.getStudentByEmail(studentEmail);

        if (student == null) {
            // Handle case where student is not found
            return "redirect:/home";
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
        return "redirect:/home";
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
    
 // Razorpay Payment Integration Methods
    @PostMapping("/create-order")
    @ResponseBody
    public ResponseEntity<Map<String, String>> createRazorpayOrder(
            @RequestParam double amount,
            @RequestParam String studentId,
            HttpSession session) {
        
        try {
            // Verify student session
            String sessionEmail = (String) session.getAttribute("studentEmail");
            if (sessionEmail == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            // Initialize Razorpay client
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
            
            // Convert amount to paise (Razorpay expects amount in smallest currency unit)
            int amountInPaise = (int) (amount * 100);
            
            // Create order request
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "fee_payment_" + studentId + "_" + System.currentTimeMillis());
            orderRequest.put("payment_capture", 1); // Auto-capture payment
            
            // Create order
            Order order = razorpay.orders.create(orderRequest);
            
            // Prepare response
            Map<String, String> response = new HashMap<>();
            response.put("orderId", order.get("id"));
            response.put("amount", String.valueOf(amountInPaise));
            response.put("razorpayKey", razorpayKeyId);
            
            return ResponseEntity.ok(response);
        } catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/verify-payment")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verifyPayment(
            @RequestBody Map<String, String> paymentData,
            HttpSession session) {
        
        try {
            // Verify student session
            String sessionEmail = (String) session.getAttribute("studentEmail");
            if (sessionEmail == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            String orderId = paymentData.get("razorpay_order_id");
            String paymentId = paymentData.get("razorpay_payment_id");
            String signature = paymentData.get("razorpay_signature");
            String studentId = paymentData.get("studentId");
            double amount = Double.parseDouble(paymentData.get("amount"));
            
            // Verify the payment signature
            boolean isValid = verifySignature(orderId, paymentId, signature);
            
            Map<String, Object> response = new HashMap<>();
            
            if (isValid) {
                // Save payment details to database
                FeePayment payment = new FeePayment();
                payment.setStudentId(studentId);
                payment.setAmount(amount);
                payment.setOrderId(orderId);
                payment.setPaymentId(paymentId);
                payment.setStatus("COMPLETED");
                payment.setPaymentDate(LocalDateTime.now());
                
                feePaymentService.savePayment(payment);
                
                response.put("success", true);
                response.put("message", "Payment verified successfully");
                response.put("paymentId", paymentId);
                
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Payment verification failed");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean verifySignature(String orderId, String paymentId, String signature) {
        try {
            String payload = orderId + "|" + paymentId;
            javax.crypto.Mac sha256_HMAC = javax.crypto.Mac.getInstance("HmacSHA256");
            javax.crypto.spec.SecretKeySpec secretKey = 
                new javax.crypto.spec.SecretKeySpec(razorpayKeySecret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secretKey);
            
            byte[] hash = sha256_HMAC.doFinal(payload.getBytes());
            String generatedSignature = java.util.Base64.getEncoder().encodeToString(hash);
            
            return generatedSignature.equals(signature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/payment-success")
    public String paymentSuccess(
            @RequestParam String paymentId, 
            @RequestParam double amount,
            Model model,
            HttpSession session) {
        
        String studentEmail = (String) session.getAttribute("studentEmail");
        if (studentEmail == null) {
            return "redirect:/home";
        }
        
        StdLogin student = stdLoginService.getStudentByEmail(studentEmail);
        if (student == null) {
            return "redirect:/error";
        }
        
        model.addAttribute("student", student);
        model.addAttribute("paymentId", paymentId);
        model.addAttribute("amount", amount);
        
        return "student/payment-success";
    }

    @GetMapping("/payment-failure")
    public String paymentFailure(Model model, HttpSession session) {
        String studentEmail = (String) session.getAttribute("studentEmail");
        if (studentEmail == null) {
            return "redirect:/home";
        }
        
        StdLogin student = stdLoginService.getStudentByEmail(studentEmail);
        if (student == null) {
            return "redirect:/error";
        }
        
        model.addAttribute("student", student);
        return "student/payment-failure";
    }

}