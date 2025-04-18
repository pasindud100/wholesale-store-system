package com.wholesale.demo.service;

import com.wholesale.demo.dto.InvoiceDTO;
import com.wholesale.demo.exception.InvoiceNotFoundException;
import com.wholesale.demo.mapper.InvoiceMapper;
import com.wholesale.demo.model.Invoice;
import com.wholesale.demo.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Transactional
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(invoice);
    }

    @Transactional(readOnly = true)
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public InvoiceDTO getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::toDTO)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with id: " + id));
    }
}