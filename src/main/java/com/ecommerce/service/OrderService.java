// backend/src/main/java/com/ecommerce/service/OrderService.java
package com.ecommerce.service;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.dto.UpdateOrderStatusRequest;
import com.ecommerce.dto.UpdatePaymentStatusRequest;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderStatus;
import com.ecommerce.model.PaymentStatus;
import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Order order = new Order(
                orderRequest.getUserId(),
                orderRequest.getTotalPrice(),
                orderRequest.getShippingAddress(),
                OrderStatus.PENDING, // Initial order status
                PaymentStatus.PENDING // Initial payment status
        );
        order = orderRepository.save(order);
        return convertToDto(order);
    }

    public OrderResponse getOrderById(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        return orderOptional.map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        order.setOrderStatus(request.getNewStatus());
        order = orderRepository.save(order);
        return convertToDto(order);
    }

    @Transactional
    public OrderResponse updatePaymentStatus(Long orderId, UpdatePaymentStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        order.setPaymentStatus(request.getNewStatus());
        order = orderRepository.save(order);
        return convertToDto(order);
    }

    private OrderResponse convertToDto(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getUserId(),
                order.getTotalPrice(),
                order.getShippingAddress(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getOrderDate()
        );
    }
}