package com.wholesale.demo.controller;

import com.wholesale.demo.dto.OrderItemDTO;
import com.wholesale.demo.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order-items") // Base endpoint
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

//    @PostMapping("/save")
//    public ResponseEntity<OrderItemDTO> saveOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
//        OrderItemDTO createdOrderItem = orderItemService.createOrderItem(orderItemDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
//    }

    @GetMapping
    public ResponseEntity<Page<OrderItemDTO>> getAllOrderItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<OrderItemDTO> orderItems = orderItemService.getAllOrderItems(page, size);
        return ResponseEntity.ok(orderItems);
    }

    //getby id
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
        OrderItemDTO orderItem = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(orderItem);
    }

    //update
    @PutMapping("/update/{id}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemDTO orderItemDTO) {
        OrderItemDTO updatedOrderItem = orderItemService.updateOrderItem(id, orderItemDTO);
        return ResponseEntity.ok(updatedOrderItem);
    }

    //delata
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItemById(id);
        return ResponseEntity.noContent().build();
    }

    //search
    @GetMapping("/search")
    public ResponseEntity<List<OrderItemDTO>> searchOrderItems(@RequestParam String searchKeyword) {
        List<OrderItemDTO> matchingCustomers = orderItemService.searchOrderItems(searchKeyword);
        return ResponseEntity.ok(matchingCustomers); // Return the matching customers with HTTP 200 OK
    }
}
