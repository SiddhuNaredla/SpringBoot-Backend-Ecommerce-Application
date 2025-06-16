package com.example.ecomproject.controller;

import com.example.ecomproject.dto.PaymentRequest;
import com.example.ecomproject.model.Cart;
import com.example.ecomproject.model.User;
import com.example.ecomproject.repository.CartRepository;
import com.example.ecomproject.repository.UserRepository;
import com.example.ecomproject.service.OrderService;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private  OrderService orderService;
    @Autowired
    private  UserRepository userRepo;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/create-payment-order")
    public ResponseEntity<?> createPaymentOrder(Principal principal) throws Exception {
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        List<Cart> cartItems = cartRepository.findByUser(user);

        double totalAmount = cartItems.stream()
                .mapToDouble(c -> c.getProduct().getPrice() * c.getQuantity())
                .sum();

        int amountInPaise = (int) (totalAmount * 100); // Razorpay needs paise

        JSONObject options = new JSONObject();
        options.put("amount", amountInPaise);
        options.put("currency", "INR");
        options.put("receipt", "order_rcptid_" + System.currentTimeMillis());
        com.razorpay.Order order = razorpayClient.orders.create(options);
        return ResponseEntity.ok(order.toString());
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody PaymentRequest paymentRequest, Principal principal) {
        try {
            User user = userRepo.findByEmail(principal.getName()).orElse(new User());

            Payment payment = razorpayClient.payments.fetch(paymentRequest.getRazorpayPaymentId());

            // Verify if payment is for correct order and captured
            if (!payment.get("order_id").equals(paymentRequest.getRazorpayOrderId())
                    || !payment.get("status").equals("captured")) {
                return ResponseEntity.badRequest().body("Payment verification failed.");
            }

            orderService.placeOrder(user, paymentRequest.getRazorpayPaymentId());
            return ResponseEntity.ok("Order placed successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/myorders")
    public ResponseEntity<?> getMyOrders(Principal principal) {
        User user = userRepo.findByEmail(principal.getName()).orElse(new User());
        return ResponseEntity.ok(orderService.getMyOrders(user));
    }


}
