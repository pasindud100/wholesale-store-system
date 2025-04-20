package com.wholesale.demo.service;

import com.wholesale.demo.dto.OrderDTO;
import com.wholesale.demo.dto.OrderItemDTO;
import com.wholesale.demo.exception.CustomerNotFoundException;
import com.wholesale.demo.exception.OrderNotFoundException;
import com.wholesale.demo.exception.ProductNotFoundException;
import com.wholesale.demo.mapper.OrderMapper;
import com.wholesale.demo.model.Customer;
import com.wholesale.demo.model.OrderItem;
import com.wholesale.demo.model.Orderss;
import com.wholesale.demo.model.Product;
import com.wholesale.demo.repository.CustomerRepository;
import com.wholesale.demo.repository.OrderRepository;
import com.wholesale.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Fetch the customer based on the provided customer ID
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + orderDTO.getCustomerId()));

        // Create a new order
        Orderss order = new Orderss();
        order.setOrderDate(LocalDateTime.now());
        order.setCustomer(customer);

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
                            throw new ProductNotFoundException("Product ID cannot be null");
                        }

                        Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
                        orderItem.setProduct(product);
                        orderItem.setOrder(order); // Set the order reference
                        return orderItem; // subtotal will be calculated in the constructor
                    })
                    .collect(Collectors.toList());
            order.setOrderItems(orderItems); // This will also calculate the total amount
        }

        // Save the order and return the DTO
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
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) throws OrderNotFoundException {
        Orderss existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        // Update existing order fields
        existingOrder.setOrderDate(LocalDateTime.now());
        existingOrder.setCustomer(customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + orderDTO.getCustomerId())));

        // Clear existing items and add updated ones
        existingOrder.getOrderItems().clear(); // Clear existing items
        if (orderDTO.getOrderItems() != null) {
            for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setQty(orderItemDTO.getQty());
                orderItem.setPrice(orderItemDTO.getPrice());

                Long productId = orderItemDTO.getProductId();
                if (productId == null) {
                    throw new ProductNotFoundException("Product ID cannot be null");
                }

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
                orderItem.setProduct(product);
                orderItem.setOrder(existingOrder); // Set the order reference
                existingOrder.addOrderItem(orderItem); // Add to the order
            }
        }

        // Save the updated order
        Orderss updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.toDTO(updatedOrder);
    }
    @Transactional
    public void deleteOrder(Long id) throws OrderNotFoundException {
        Orderss existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        orderRepository.delete(existingOrder);
    }
}