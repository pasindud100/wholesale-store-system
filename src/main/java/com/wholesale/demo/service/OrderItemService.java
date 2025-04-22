package com.wholesale.demo.service;

import com.wholesale.demo.dto.OrderItemDTO;
import com.wholesale.demo.exception.OrderItemNotFoundException;
import com.wholesale.demo.exception.ProductNotFoundException;
import com.wholesale.demo.exception.ResourceNotFoundException;
import com.wholesale.demo.mapper.OrderItemMapper;
import com.wholesale.demo.model.OrderItem;
import com.wholesale.demo.model.Orderss;
import com.wholesale.demo.model.Product;
import com.wholesale.demo.repository.OrderItemRepository;
import com.wholesale.demo.repository.OrderRepository;
import com.wholesale.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, ProductRepository productRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        Long orderId = orderItemDTO.getOrderId();
        Long productId = orderItemDTO.getProductId();

        // check order and product are exist from search ids. If Those are not exist custom exceptions.
        Orderss order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderItemNotFoundException("Order not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Check if there is enough stock to fulfill the order
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
        orderItem = orderItemRepository.save(orderItem); // Save the order item

        // Update the order amount
        order.setAmount(order.getAmount() + subtotal);
        orderRepository.save(order); // Save updated order amount

        // Mapping to dto and return
        return orderItemMapper.toDTO(orderItem);
    }

    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(orderItemMapper::toDTO)
                .toList();
    }

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
}