package com.shopflow.orderservice.service;

import com.shopflow.orderservice.model.Order;
import com.shopflow.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "order_created_events";

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        kafkaTemplate.send(TOPIC, savedOrder.getId().toString()); // Publish order ID to Kafka
        return savedOrder;
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderItems(orderDetails.getOrderItems());
        order.setStatus(orderDetails.getStatus());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
