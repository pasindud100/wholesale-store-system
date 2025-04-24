package com.wholesale.demo.controller;

import com.wholesale.demo.dto.CustomerDTO;
import com.wholesale.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * CustomerController handles HTTP requests related to customer operations.
 * It provides endpoints for creating, retrieving, updating, and deleting customers.
 */
@RestController
@RequestMapping("/api/v1/customers") // Base URL for customer-related operations
public class CustomerController {

    @Autowired
    private CustomerService customerService; // Service layer for customer operations

    /**
     * Endpoint to save a new customer.
     * @param customerDTO the customer data transfer object containing customer details
     * @return ResponseEntity containing the saved CustomerDTO
     */
    @PostMapping("/save")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerService.saveCustomer(customerDTO);
        return ResponseEntity.status(201).body(savedCustomer);
    }
    /**
     * Endpoint to retrieve all customers with pagination.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of customers per page (default is 6)
     * @return ResponseEntity containing a paginated list of CustomerDTOs
     */
    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<CustomerDTO> customers = customerService.getAllCustomers(page, size);
        return ResponseEntity.ok(customers); // Return the paginated customers with HTTP 200 OK
    }

    /**
     * Endpoint to retrieve a customer by their ID.
     * @param id the ID of the customer to retrieve
     * @return ResponseEntity containing the CustomerDTO if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable long id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer); // Return the customer with HTTP 200 OK
    }

    /**
     * Endpoint to update an existing customer.
     * @param customerDTO the updated customer data transfer object
     * @param id the ID of the customer to update
     * @return ResponseEntity containing the updated CustomerDTO
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable long id) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer); // Return the updated customer with HTTP 200 OK
    }

    /**
     * Endpoint to delete a customer by their ID
     * @param id the ID of the customer to delete
     * @return ResponseEntity containing the deleted CustomerDTO
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable long id) {
        CustomerDTO deletedCustomer = customerService.deleteCustomer(id);
        return ResponseEntity.status(204).body(deletedCustomer);
    }

    /**
     * Endpoint to search for customers based on a search keyword.
     * @param searchKeyword the keyword to search for in customer fields
     * @return ResponseEntity containing a list of matching CustomerDTOs
     */
    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomers(@RequestParam String searchKeyword) {
        List<CustomerDTO> matchingCustomers = customerService.searchCustomers(searchKeyword);
        return ResponseEntity.ok(matchingCustomers); // Return the matching customers with HTTP 200 OK
    }
}