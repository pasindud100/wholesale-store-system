package com.wholesale.demo.controller;

import com.wholesale.demo.dto.InvoiceDTO;
import com.wholesale.demo.exception.OrderNotFoundException;
import com.wholesale.demo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices") // Base path
public class InvoiceController {

    @Autowired // Automatically injects the InvoiceService dependency
    private InvoiceService invoiceService;

    //save
    @PostMapping("/save")
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceDTO invoiceDTO) throws OrderNotFoundException {
        InvoiceDTO createdInvoice = invoiceService.createInvoice(invoiceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoice);
    }

    //get all
    @GetMapping
    public ResponseEntity<Page<InvoiceDTO>> getAllInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<InvoiceDTO> invoices = invoiceService.getAllInvoices(page, size);
        return ResponseEntity.ok(invoices);
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Long id) {
        InvoiceDTO invoice = invoiceService.getInvoiceById(id);
        return invoice != null ? ResponseEntity.ok(invoice) : ResponseEntity.notFound().build();
    }

    //update
    @PutMapping("/update/{id}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable Long id, @RequestBody InvoiceDTO invoiceDTO) throws OrderNotFoundException {
        InvoiceDTO updatedInvoice = invoiceService.updateInvoice(id, invoiceDTO);
        return ResponseEntity.ok(updatedInvoice);
    }

    //search
    @GetMapping("/search")
    public ResponseEntity<List<InvoiceDTO>> searchInvoices(@RequestParam String searchKeyword) {
        List<InvoiceDTO> matchingInvoices = invoiceService.searchInvoices(searchKeyword);
        return ResponseEntity.ok(matchingInvoices); // Return matching invoices
    }
}
