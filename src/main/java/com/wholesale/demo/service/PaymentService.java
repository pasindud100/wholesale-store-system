package com.wholesale.demo.service;

import com.wholesale.demo.dto.PaymentDTO;
import com.wholesale.demo.exception.*;
import com.wholesale.demo.mapper.PaymentMapper;
import com.wholesale.demo.model.Invoice;
import com.wholesale.demo.model.Payment;
import com.wholesale.demo.repository.InvoiceRepository;
import com.wholesale.demo.repository.OrderRepository;
import com.wholesale.demo.repository.PaymentRepository;
import com.wholesale.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public PaymentDTO createPayment(PaymentDTO paymentDTO)
            throws OrderNotFoundException, InvoiceNotFoundException, InvoiceAlreadyPaidException {

        // Check if the order exists
        orderRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + paymentDTO.getOrderId()));

        // Check if the invoice exists
        Invoice isInvoiceExist = invoiceRepository.findById(paymentDTO.getInvoiceId())
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with id: " + paymentDTO.getInvoiceId()));

        // Check if payment already exists for the order
        if (paymentRepository.findByOrderId(paymentDTO.getOrderId()).isPresent()) {
            throw new InvoiceAlreadyPaidException("Payment already made for order ID: " + paymentDTO.getOrderId());
        }

        if(paymentRepository.findByInvoiceId(paymentDTO.getInvoiceId()).isPresent()) {
            throw new InvoiceAlreadyPaidException("Payment already made for invoice ID: " +
                    paymentDTO.getInvoiceId());
        }

        double paidDifference = isInvoiceExist.getAmount() - paymentDTO.getPaidAmount();

        if (paidDifference < 0) {
            throw new AmountGreateOrLessException("Invoice amount is less than paying amount "+
                    paymentDTO.getPaidAmount()+" " + ". Get remain amount " +
                    Math.abs(paymentDTO.getPaidAmount()) + " to complete the invoice payment.");
        }
        if(paidDifference > 0) {
            throw new AmountGreateOrLessException("Invoice amount is grater than paying amount "
                    +paymentDTO.getPaidAmount() + " . Add remain amount " +
                    Math.abs(paymentDTO.getPaidAmount()) + " to complete the invoice payment.");
        }

        Payment payment = paymentMapper.toEntity(paymentDTO);
        payment = paymentRepository.save(payment);
        return paymentMapper.toDTO(payment);
    }

    public Page<PaymentDTO> getAllPayments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> payments = paymentRepository.findAll(pageable);
        return payments.map(paymentMapper::toDTO);
    }

    public PaymentDTO getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toDTO)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
    }

    public List<PaymentDTO> searchPayments(String searchKeyword) {
        List<Payment> payments = paymentRepository.searchPayments(searchKeyword);
        return payments.stream()
                .map(paymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentDTO updatePayment(Long id ,PaymentDTO paymentDTO) {
        Payment paymentToUpdate = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));

        orderRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(()-> new PaymentNotFoundException("Order not found with id: " + paymentDTO.getOrderId()));

        invoiceRepository.findById(paymentDTO.getInvoiceId())
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with id: " + paymentDTO.getInvoiceId()));

        paymentToUpdate = paymentMapper.toEntity(paymentDTO);
        paymentToUpdate.setId(id);
        paymentRepository.save(paymentToUpdate);
        return paymentMapper.toDTO(paymentToUpdate);
    }

    @Transactional
    public void deletePayment(Long id) throws OrderNotFoundException {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        paymentRepository.delete(existingPayment);
    }
}