package com.wholesale.demo.service;

import com.wholesale.demo.dto.OrderDTO;
import com.wholesale.demo.exception.CustomerNotFoundException;
import com.wholesale.demo.exception.OrderNotFoundException;
import com.wholesale.demo.mapper.OrderMapper;
import com.wholesale.demo.model.Customer;
import com.wholesale.demo.model.Orderss;
import com.wholesale.demo.repository.CustomerRepository;
import com.wholesale.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + orderDTO.getCustomerId()));

        Orderss order = new Orderss();
        order.setOrderDate(LocalDateTime.now());
        order.setCustomer(customer);
        order.setAmount(0); // Initial amount set to 0, will be updated later

        Orderss savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        List<Orderss> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) throws OrderNotFoundException {
        Orderss order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        return orderMapper.toDTO(order);
    }

    @Transactional
    public void deleteOrder(Long id) throws OrderNotFoundException {
        Orderss existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        orderRepository.delete(existingOrder);
    }

    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) throws OrderNotFoundException {
        Orderss existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        // Update existing order fields
        existingOrder.setOrderDate(orderDTO.getOrderDate());
        existingOrder.setCustomer(customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + orderDTO.getCustomerId())));

        Orderss updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.toDTO(updatedOrder);
    }
}