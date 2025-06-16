package com.example.ecomproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {

    private int id;
    @NotNull
    private int productId;
    @NotNull
    private int quantity;
}
