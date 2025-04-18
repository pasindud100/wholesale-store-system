package com.wholesale.demo.service;

import com.wholesale.demo.dto.OrderItemDTO;
import com.wholesale.demo.exception.OrderItemNotFoundException;
import com.wholesale.demo.mapper.OrderItemMapper;
import com.wholesale.demo.model.OrderItem;
import com.wholesale.demo.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Transactional
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
        orderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toDTO(orderItem);
    }

    @Transactional(readOnly = true)
    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(orderItemMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderItemDTO getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .map(orderItemMapper::toDTO)
                .orElseThrow(() -> new OrderItemNotFoundException("No OrderItem found with id: " + id));
    }
}