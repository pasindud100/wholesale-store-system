package com.wholesale.demo.service;

import com.wholesale.demo.dto.CustomerDTO;
import com.wholesale.demo.exception.CustomerNotFoundException;
import com.wholesale.demo.mapper.CustomerMapper;
import com.wholesale.demo.model.Customer;
import com.wholesale.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;

    @Transactional
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }

    public Page<CustomerDTO> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = customerRepository.findAll(pageable);
        return customersPage.map(customerMapper::toDTO);
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return customerMapper.toDTO(customer);
    }

    @Transactional
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customerToUpdate = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        customerToUpdate = customerMapper.toEntity(customerDTO);
        customerToUpdate.setId(id);
        Customer updatedCustomer = customerRepository.save(customerToUpdate);
        return customerMapper.toDTO(updatedCustomer);
    }

    public CustomerDTO deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        customerRepository.deleteById(id);
        return customerMapper.toDTO(customer); // to return deleted customer details
    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> searchCustomers(String searchKeyword) {
        List<Customer> customers = customerRepository.searchCustomers(searchKeyword);
        return customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }
}