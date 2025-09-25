package com.system.project.repository;

import com.system.project.entities.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
    FeePayment findByPaymentId(String paymentId);
    FeePayment findByOrderId(String orderId);
    List<FeePayment> findByStudentId(String studentId);
}