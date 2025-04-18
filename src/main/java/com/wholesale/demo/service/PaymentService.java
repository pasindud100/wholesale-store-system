package com.wholesale.demo.service;

import com.wholesale.demo.dto.PaymentDTO;
import com.wholesale.demo.exception.PaymentNotFoundException;
import com.wholesale.demo.mapper.PaymentMapper;
import com.wholesale.demo.model.Payment;
import com.wholesale.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Transactional
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = paymentMapper.toEntity(paymentDTO);
        payment = paymentRepository.save(payment);
        return paymentMapper.toDTO(payment);
    }

    @Transactional(readOnly = true)
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PaymentDTO getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toDTO)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
    }
}