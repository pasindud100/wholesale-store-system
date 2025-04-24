package com.wholesale.demo.service;

import com.wholesale.demo.dto.CustomerDTO;
import com.wholesale.demo.dto.OrderDTO;
import com.wholesale.demo.dto.OrderItemDTO;
import com.wholesale.demo.exception.OrderItemNotFoundException;
import com.wholesale.demo.exception.ProductNotFoundException;
import com.wholesale.demo.exception.ResourceNotFoundException;
import com.wholesale.demo.mapper.OrderItemMapper;
import com.wholesale.demo.model.Customer;
import com.wholesale.demo.model.OrderItem;
import com.wholesale.demo.model.Orderss;
import com.wholesale.demo.model.Product;
import com.wholesale.demo.repository.OrderItemRepository;
import com.wholesale.demo.repository.OrderRepository;
import com.wholesale.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        Long orderId = orderItemDTO.getOrderId();
        Long productId = orderItemDTO.getProductId();

        Orderss order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderItemNotFoundException("Order not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (product.getStock() < orderItemDTO.getQty()) {
            throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
        }

        // Create the order item
        OrderItem orderItem = new OrderItem();
        orderItem.setQty(orderItemDTO.getQty());
        orderItem.setPrice(orderItemDTO.getPrice());
        orderItem.setOrder(order); // Set the order
        orderItem.setProduct(product);

        //Updating  product stock
        product.setStock(product.getStock() - orderItemDTO.getQty());
        productRepository.save(product);

        //Calculate subtotal and set it
        double subtotal = orderItem.getPrice() * orderItem.getQty();
        orderItem.setSubtotal(subtotal);
        orderItem = orderItemRepository.save(orderItem);

        // Update the order amount
        order.setAmount(order.getAmount() + subtotal);
        orderRepository.save(order); // Save updated order amount

        return orderItemMapper.toDTO(orderItem);
    }

    @Transactional(readOnly = true)
    public Page<OrderItemDTO> getAllOrderItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderItem> orderItems = orderItemRepository.findAll(pageable);
        return orderItems.map(orderItemMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public OrderItemDTO getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found"));//give exception
        return orderItemMapper.toDTO(orderItem);
    }

    @Transactional
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        // getting the existing order item
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found with id: " + id));

        // Store the previous quantity for stock adjustment
        int previousQty = existingOrderItem.getQty();

        // Updating the order item fields
        existingOrderItem.setQty(orderItemDTO.getQty());
        existingOrderItem.setPrice(orderItemDTO.getPrice());

        // Retrieve the product to update stock
        Product product = productRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Check if the stock is sufficient for the new quantity
        if (product.getStock() + previousQty < orderItemDTO.getQty()) {
            throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
        }

        //Update product stock add the previous qty to existing qty and subtract the new qty
        product.setStock(product.getStock() + previousQty - orderItemDTO.getQty());
        productRepository.save(product);

        //Calculating the new subtotal
        double subtotal = existingOrderItem.getPrice() * existingOrderItem.getQty();
        existingOrderItem.setSubtotal(subtotal); // Update the subtotal in the order item

        // Save the updated order item
        existingOrderItem = orderItemRepository.save(existingOrderItem);

        // Update the order amount
        Orderss order = existingOrderItem.getOrder();
        // set by subtract the previous subtotal and add the new subtotal
        order.setAmount(order.getAmount() - (previousQty * existingOrderItem.getPrice()) + subtotal);
        orderRepository.save(order); //Saving updated order amount

        return orderItemMapper.toDTO(existingOrderItem);
    }

    @Transactional
    public void deleteOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found"));
        Orderss order = orderItem.getOrder();
        double subtotal = orderItem.getSubtotal();

        // Update order amount in order table
        order.setAmount(order.getAmount() - subtotal);
        orderRepository.save(order);
        orderItemRepository.delete(orderItem);
    }

    @Transactional(readOnly = true)
    public List<OrderItemDTO> searchOrderItems(String searchKeyword) {
        List<OrderItem> orderItems = orderItemRepository.searchOrderItems(searchKeyword);
        return orderItems.stream()
                .map(orderItemMapper::toDTO)
                .collect(Collectors.toList());
    }
}