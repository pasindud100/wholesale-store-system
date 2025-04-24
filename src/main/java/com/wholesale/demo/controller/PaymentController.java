package com.wholesale.demo.controller;

import com.wholesale.demo.dto.PaymentDTO;
import com.wholesale.demo.exception.OrderNotFoundException;
import com.wholesale.demo.repository.PaymentRepository;
import com.wholesale.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping("/save")
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) throws OrderNotFoundException {
        PaymentDTO createdPayment = paymentService.createPayment(paymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDTO>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<PaymentDTO> payments = paymentService.getAllPayments(page,size);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        PaymentDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO updatedPayment = paymentService.updatePayment(id,paymentDTO);
        return ResponseEntity.ok(updatedPayment);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PaymentDTO>> searchPayments(@RequestParam String searchKeyword) {
        List<PaymentDTO> matchingCustomers = paymentService.searchPayments(searchKeyword);
        return ResponseEntity.ok(matchingCustomers); // Return the matching customers with HTTP 200 OK
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) throws OrderNotFoundException {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}