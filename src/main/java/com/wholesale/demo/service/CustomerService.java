package com.wholesale.demo.service;

import com.wholesale.demo.dto.CustomerDTO;
import com.wholesale.demo.mapper.CustomerMapper;
import com.wholesale.demo.model.Customer;
import com.wholesale.demo.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository , CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }

//    public List<CustomerDTO> getAllCustomers() {
//        return customerRepository.findAll()
//        .stream().map(customerMapper::toDTO)
//        .collect(Collectors.toList());
//    }

    public Page<CustomerDTO> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = customerRepository.findAll(pageable);

        return customersPage.map(customerMapper::toDTO);
    }

    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(customerMapper::toDTO).orElse(null);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Optional<Customer> customer = customerRepository.findById(id);

        if(customer.isPresent()) {
            Customer customerToUpdate = customerMapper.toEntity(customerDTO);
            customerToUpdate.setId(id);
            Customer updatedCustomer = customerRepository.save(customerToUpdate);
            return customerMapper.toDTO(updatedCustomer);
        }
        return null;

    }
    public CustomerDTO deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            customerRepository.deleteById(id); // Delete the customer
            return customerMapper.toDTO(customer.get()); // Return the deleted customer details
        }
        return null; //give exception if the customer is not found
    }

    public List<CustomerDTO> searchCustomers(String searchKeyword) {
        List<Customer> customers = customerRepository.searchCustomers(searchKeyword);
        return customers.stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }





}