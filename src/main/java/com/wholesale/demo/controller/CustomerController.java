package com.wholesale.demo.controller;

import com.wholesale.demo.dto.CustomerDTO;
import com.wholesale.demo.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //save
    @PostMapping("/save")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO savedCustomer = customerService.saveCustomer(customerDTO);
        return ResponseEntity.ok(savedCustomer);
    }

//    @GetMapping
//    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
//        List<CustomerDTO> customers = customerService.getAllCustomers();
//        return ResponseEntity.ok(customers);
//    }

    //get all customers with pagination
    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CustomerDTO> customers = customerService.getAllCustomers(page, size);
        return ResponseEntity.ok(customers);
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable long id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    //update
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable long id) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    //delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable long id) {
        CustomerDTO deletedCustomer = customerService.deleteCustomer(id);
        return ResponseEntity.ok(deletedCustomer);
    }
}