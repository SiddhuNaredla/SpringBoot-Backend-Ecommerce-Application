package com.example.ecomproject.controller;

import com.example.ecomproject.common.ApiResponse;
import com.example.ecomproject.dto.AddToCartDto;
import com.example.ecomproject.dto.CartDto;
import com.example.ecomproject.dto.CartItemsDto;
import com.example.ecomproject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, Principal principal){
        String message=cartService.addToCart(addToCartDto,principal);
        return new ResponseEntity<>(new ApiResponse(true, "message"), HttpStatus.CREATED);
    }

    @GetMapping("/allitems")
    public ResponseEntity<CartDto> getCartItems(Principal principal){
        CartDto items=cartService.getCartItems(principal);
        return new ResponseEntity<>(items,HttpStatus.OK);
    }

    @DeleteMapping("/deleteitem/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable int cartItemId){
        String message=cartService.deleteFromCart(cartItemId);
        return new ResponseEntity<>(new ApiResponse(true, message),HttpStatus.OK);
    }

}
