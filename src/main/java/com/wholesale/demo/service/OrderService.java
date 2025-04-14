package com.wholesale.demo.service;

import com.wholesale.demo.dto.OrderDTO;
import com.wholesale.demo.mapper.OrderMapper;
import com.wholesale.demo.model.Customer;
import com.wholesale.demo.model.OrderItem;
import com.wholesale.demo.model.Orderss;
import com.wholesale.demo.model.Product;
import com.wholesale.demo.repository.CustomerRepository;
import com.wholesale.demo.repository.OrderRepository;
import com.wholesale.demo.repository.ProductRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,ProductRepository productRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

//    @Transactional
//    public OrderDTO createOrder(OrderDTO orderDTO) {
//        Optional<Customer> customer = customerRepository.findById(orderDTO.getCustomerId());
//
//        if (!customer.isPresent()) {
//            throw new RuntimeException("Customer not found");
//        }
//
//        Orderss order = new Orderss();
//        order.setOrderDate(LocalDateTime.now());
//        order.setCustomer(customer.get());
//
//        Orderss savedOrder = orderRepository.save(order);
//        return orderMapper.toDTO(savedOrder);
//    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Optional<Customer> customer = customerRepository.findById(orderDTO.getCustomerId());

        if (!customer.isPresent()) {
            throw new RuntimeException("Customer not found");
        }

        Orderss order = new Orderss();
        order.setOrderDate(LocalDateTime.now());
        order.setCustomer(customer.get());

        // Set the order items
        if (orderDTO.getOrderItems() != null) {
            List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
                    .map(orderItemDTO -> {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setQty(orderItemDTO.getQty());
                        orderItem.setPrice(orderItemDTO.getPrice());

                        // Fetch the product based on productId
                        Long productId = orderItemDTO.getProductId();
                        if (productId == null) {
                            throw new RuntimeException("Product ID cannot be null");
                        }

                        Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));
                        orderItem.setProduct(product);
                        orderItem.setOrder(order); // Set the order reference
                        return orderItem;
                    })
                    .collect(Collectors.toList());
            order.setOrderItems(orderItems);
        }

        Orderss savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }
    public List<Orderss> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<OrderDTO> getAllOrders() {
        List<Orderss> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Orderss order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDTO(order);
    }

    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Orderss existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Update fields as necessary
        existingOrder.setOrderDate(LocalDateTime.now());

        Orderss updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.toDTO(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Orderss existingOrder = orderRepository.findById(id )
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(existingOrder);
    }
}