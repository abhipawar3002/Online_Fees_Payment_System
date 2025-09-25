package com.system.project.service;

import com.system.project.entities.FeePayment;
import com.system.project.repository.FeePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeePaymentService {
    
    private final FeePaymentRepository feePaymentRepository;

    @Autowired
    public FeePaymentService(FeePaymentRepository feePaymentRepository) {
        this.feePaymentRepository = feePaymentRepository;
    }
    
    public FeePayment savePayment(FeePayment payment) {
        // Generate receipt number before saving
        if(payment.getReceiptNumber() == null) {
            payment.setReceiptNumber(generateReceiptNumber());
        }
        return feePaymentRepository.save(payment);
    }
    
    public FeePayment getPaymentById(Long id) {
        return feePaymentRepository.findById(id).orElse(null);
    }
    
    public FeePayment getPaymentByPaymentId(String paymentId) {
        return feePaymentRepository.findByPaymentId(paymentId);
    }
    
    public FeePayment getPaymentByOrderId(String orderId) {
        return feePaymentRepository.findByOrderId(orderId);
    }
    
    public List<FeePayment> getPaymentsByStudentId(String studentId) {
        return feePaymentRepository.findByStudentId(studentId);
    }
    
    private String generateReceiptNumber() {
        long count = feePaymentRepository.count();
        return "RCPT-" + LocalDateTime.now().getYear() + "-" + 
               String.format("%06d", count + 1);
    }
}