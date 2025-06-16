package com.example.ecomproject.service;

import com.example.ecomproject.dto.OrderResponseDto;
import com.example.ecomproject.model.*;
import com.example.ecomproject.repository.CartRepository;
import com.example.ecomproject.repository.OrderItemRepository;
import com.example.ecomproject.repository.OrderRepository;
import com.example.ecomproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<OrderResponseDto> getMyOrders(User user) {
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderResponseDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItem> cleanedItems = new ArrayList<>();

            for (OrderItem item : order.getItems()) {
                OrderItem cleanedItem = new OrderItem();
                cleanedItem.setProduct(new Product(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getImageUrl(),
                        item.getProduct().getPrice(),
                        item.getProduct().getDescription(),
                        item.getProduct().getCategory()
                ));
                cleanedItem.setQuantity(item.getQuantity());
                cleanedItems.add(cleanedItem);
            }

            orderDtos.add(new OrderResponseDto(
                    order.getId(),
                    order.getTotalAmount(),
                    order.getRazorpayPaymentId(),
                    order.getCreatedDate(),
                    cleanedItems
            ));
        }

        return orderDtos;
    }

    @Transactional
    public void placeOrder(User user, String razorpayPaymentId) {
        List<Cart> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) throw new RuntimeException("Cart is empty!");

        Order order = new Order();
        order.setUser(user);
        order.setCreatedDate(new Date());
        order.setRazorpayPaymentId(razorpayPaymentId);

        double total = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (Cart cart : cartItems) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(cart.getProduct());
            item.setQuantity(cart.getQuantity());
            total += cart.getProduct().getPrice() * cart.getQuantity();
            orderItems.add(item);
        }

        order.setTotalAmount(total);
        order.setItems(orderItems);

        orderRepository.save(order);
        cartRepository.deleteByUser(user); // clear cart
    }
}
