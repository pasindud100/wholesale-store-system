package com.wholesale.demo.service;

import com.wholesale.demo.dto.*;
import com.wholesale.demo.exception.*;
import com.wholesale.demo.mapper.InvoiceMapper;
import com.wholesale.demo.mapper.OrderItemMapper;
import com.wholesale.demo.mapper.OrderMapper;
import com.wholesale.demo.mapper.PaymentMapper;
import com.wholesale.demo.model.*;
import com.wholesale.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {

        // Create Order entity
        Orderss order = new Orderss();
        order.setOrderDate(orderDTO.getOrderDate()); // set order date to order

        // check and set customer
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + orderDTO.getCustomerId()));
        order.setCustomer(customer);

        double totalAmount = 0.0;

        List<OrderItem> orderItemList = new ArrayList<>();
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        // Process order items..check product existence, check and deduct stock count, set total amount
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            OrderItem item = new OrderItem();

            // check product is exist and retrieve the product and set it
            Product isProductExist = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new PaymentNotFoundException("Product not found with id: " + itemDTO.getProductId()));
            item.setProduct(isProductExist); // Set the product

            //check stock count availability
            if (isProductExist.getStock() < itemDTO.getQty()) {
                throw  new OutOfStockException("Stock has only " + itemDTO.getQty() +
                        " items from product " + itemDTO.getProductId() + ".");
            }

            //deduct ordered items qty from product stock
            isProductExist.setStock(isProductExist.getStock() - itemDTO.getQty());
            productRepository.save(isProductExist);

            // Set values from DTO
            item.setQty(itemDTO.getQty());
            item.setPrice(itemDTO.getPrice());

            // Calculate subtotal
            double subtotal = itemDTO.getQty() * itemDTO.getPrice();
            item.setSubtotal(subtotal);

            totalAmount += subtotal;

            item.setOrder(order); // this is associate with order
            orderItemList.add(item);

        }

        // Set total to order
        order.setAmount(totalAmount);

        // Save order first
        Orderss savedOrder = orderRepository.save(order);

        // Save order items
        List<OrderItem> savedOrderItems = orderItemRepository.saveAll(orderItemList);

        // Prepare dtos for order items
        for (OrderItem savedItem : savedOrderItems) {

            OrderItemDTO savedItemDTO = orderItemMapper.toDTO(savedItem);
            orderItemDTOs.add(savedItemDTO);
        }

        // Save invoice
        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(orderDTO.getOrderDate());
        invoice.setOrder(savedOrder);
        invoice.setAmount(totalAmount);
        invoiceRepository.save(invoice);

        // Save payment
        Payment payment = new Payment();
        payment.setPaymentDate(orderDTO.getOrderDate());
        payment.setPaidAmount(orderDTO.getPayment().getPaidAmount());
        payment.setPaymentMethod(orderDTO.getPayment().getPaymentMethod());
        payment.setOrder(savedOrder);
        payment.setInvoice(invoice);
        paymentRepository.save(payment);

        OrderDTO savedOrderDTO = orderMapper.toDTO(savedOrder);
        savedOrderDTO.setOrderItems(orderItemDTOs);

        InvoiceDTO invoiceDTO = invoiceMapper.toDTO(invoice);
        savedOrderDTO.setInvoice(invoiceDTO);

        PaymentDTO paymentDTO = paymentMapper.toDTO(payment);
        savedOrderDTO.setPayment(paymentDTO);

        return savedOrderDTO;
    }

    @Transactional
    public List<FullOrderView> getFilteredOrders(Long orderId, Long customerId, Long invoiceId, Long paymentId) throws OrderNotFoundException {
        List<FullOrderView> orders = orderRepository.findFilteredOrders(orderId, customerId, invoiceId, paymentId);

        // Check if the result is empty
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found matching the provided criteria.");
        }

        return orders;
    }

    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) throws OrderNotFoundException {

        // Find the existing order
        Orderss existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        // Update order date
        existingOrder.setOrderDate(orderDTO.getOrderDate());

        // Update customer if needed
        if (orderDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + orderDTO.getCustomerId()));
            existingOrder.setCustomer(customer);
        }

        // Reset the total amount
        double totalAmount = 0.0;

        // Delete existing order items first
        List<OrderItem> existingItems = orderItemRepository.findByOrder(existingOrder);
        for (OrderItem item : existingItems) {
            // Restore stock back
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQty());
            productRepository.save(product);
        }
        orderItemRepository.deleteAll(existingItems);

        // Prepare new order items
        List<OrderItem> newOrderItems = new ArrayList<>();
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            OrderItem newItem = new OrderItem();

            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + itemDTO.getProductId()));

            // Check stock
            if (product.getStock() < itemDTO.getQty()) {
                throw new OutOfStockException("Stock has only " + product.getStock() + " items from product " + itemDTO.getProductId() + ".");
            }

            // Deduct new qty from stock
            product.setStock(product.getStock() - itemDTO.getQty());
            productRepository.save(product);

            // Set item details
            newItem.setProduct(product);
            newItem.setQty(itemDTO.getQty());
            newItem.setPrice(itemDTO.getPrice());

            double subtotal = itemDTO.getQty() * itemDTO.getPrice();
            newItem.setSubtotal(subtotal);

            totalAmount += subtotal;

            newItem.setOrder(existingOrder);
            newOrderItems.add(newItem);
        }

        // Set new amount to order
        existingOrder.setAmount(totalAmount);

        // Save updated order
        Orderss updatedOrder = orderRepository.save(existingOrder);

        // Save new order items
        List<OrderItem> savedItems = orderItemRepository.saveAll(newOrderItems);

        // Map order items to DTO
        for (OrderItem savedItem : savedItems) {
            OrderItemDTO savedItemDTO = orderItemMapper.toDTO(savedItem);
            orderItemDTOs.add(savedItemDTO);
        }

        // Update Invoice
        Invoice invoice = invoiceRepository.findByOrder(updatedOrder)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found for order id: " + id));
        invoice.setInvoiceDate(orderDTO.getOrderDate());
        invoice.setAmount(totalAmount);
        invoiceRepository.save(invoice);

        // Update Payment
        Payment payment = paymentRepository.findByOrder(updatedOrder)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for order id: " + id));
        payment.setPaymentDate(orderDTO.getOrderDate());
        payment.setPaidAmount(orderDTO.getPayment().getPaidAmount());
        payment.setPaymentMethod(orderDTO.getPayment().getPaymentMethod());
        payment.setInvoice(invoice);
        paymentRepository.save(payment);

        // Prepare return DTO
        OrderDTO updatedOrderDTO = orderMapper.toDTO(updatedOrder);
        updatedOrderDTO.setOrderItems(orderItemDTOs);

        InvoiceDTO invoiceDTO = invoiceMapper.toDTO(invoice);
        updatedOrderDTO.setInvoice(invoiceDTO);

        PaymentDTO paymentDTO = paymentMapper.toDTO(payment);
        updatedOrderDTO.setPayment(paymentDTO);

        return updatedOrderDTO;
    }
}