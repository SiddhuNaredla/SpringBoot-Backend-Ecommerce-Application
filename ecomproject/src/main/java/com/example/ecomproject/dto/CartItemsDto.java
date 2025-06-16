package com.example.ecomproject.dto;

import com.example.ecomproject.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsDto {

    private int id;
    private int quantity;
    private Product prodcut;
}
