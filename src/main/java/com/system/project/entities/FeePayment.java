package com.system.project.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fee_payments")
public class FeePayment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "student_id", nullable = false)
    private String studentId;
    
    @Column(nullable = false)
    private Double amount;
    
    @Column(name = "order_id", unique = true)
    private String orderId;
    
    @Column(name = "payment_id", unique = true)
    private String paymentId;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;
    
    @Column(name = "receipt_number")
    private String receiptNumber;
    
    // Constructors
    public FeePayment() {
    }

    public FeePayment(String studentId, Double amount, String orderId, 
                    String paymentId, String status, LocalDateTime paymentDate) {
        this.studentId = studentId;
        this.amount = amount;
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.status = status;
        this.paymentDate = paymentDate;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
}