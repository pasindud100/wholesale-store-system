package com.wholesale.demo.controller;

import com.wholesale.demo.dto.FullOrderView;
import com.wholesale.demo.dto.OrderDTO;
import com.wholesale.demo.exception.OrderNotFoundException;
import com.wholesale.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders") // Base endpoint for order APIs
public class OrderController {

    @Autowired // Injects the OrderService dependency
    private OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/search")
    public List<FullOrderView> searchOrders(
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long invoiceId,
            @RequestParam(required = false) Long paymentId
    ) throws OrderNotFoundException {
        return orderService.getFilteredOrders(orderId, customerId, invoiceId, paymentId);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) throws OrderNotFoundException {
        OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok(updatedOrder);
    }
}
