package com.example.ecomproject.controller;

import com.example.ecomproject.common.ApiResponse;
import com.example.ecomproject.dto.ProductDto;
import com.example.ecomproject.model.Product;
import com.example.ecomproject.service.WishListService;
import org.hibernate.dialect.function.DB2SubstringFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @PostMapping("/addtowishlist")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, Principal principal){
        String message=wishListService.addToWishList(product,principal);
        return new ResponseEntity<>(new ApiResponse(true,message),HttpStatus.CREATED);
    }

    @GetMapping("getwishlist")
    public ResponseEntity<List<ProductDto>> getWishList(Principal principal){
        List<ProductDto> products=wishListService.getWishList(principal);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("removeitem/{productId}")
    public ResponseEntity<ApiResponse> removeFromWishList(@PathVariable Integer productId,Principal principal){
        String message=wishListService.removeFromWishList(productId,principal);
        return new ResponseEntity<>(new ApiResponse(true,message),HttpStatus.OK);
    }

}
