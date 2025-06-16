package com.example.ecomproject.dto;

import com.example.ecomproject.model.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private int orderId;
    private double totalAmount;
    private String razorpayPaymentId;
    private Date createdDate;
    private List<OrderItem> items;
}
