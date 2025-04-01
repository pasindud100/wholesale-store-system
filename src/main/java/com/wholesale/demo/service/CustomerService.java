package com.wholesale.demo.service;



import com.wholesale.demo.dto.CustomerDTO;
import com.wholesale.demo.mapper.CustomerMapper;
import com.wholesale.demo.model.Customer;
import com.wholesale.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository , CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toDTO).collect(Collectors.toList());
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


}

