package com.example.keycloak_spring_unit1.controller;

import com.example.keycloak_spring_unit1.entity.Order;
import com.example.keycloak_spring_unit1.entity.OrderItem;
import com.example.keycloak_spring_unit1.repository.OrderItemRepository;
import com.example.keycloak_spring_unit1.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @GetMapping("/{restaurantId}/list")
    private List<Order> getOrders(
            @PathVariable Long restaurantId
    ) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    @GetMapping("/{orderId}")
    public Order getOrderDetails(
            @PathVariable Long orderId
    ) {
        Order order = orderRepository.findById(orderId).get();
        order.setOrderItems(orderItemRepository.findByOrderId(order.getId()));
        return order;
    }

    @PostMapping
    // authenticated users can access
    public Order createOrder(Order order) {
        orderRepository.save(order);
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem -> {
            orderItem.setOrderId(order.getId());
            orderItemRepository.save(orderItem);
        });
        return order;
    }
}
