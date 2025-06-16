package com.example.ecomproject.exceptions;

public class ProductNotFoundException extends IllegalArgumentException{
    public ProductNotFoundException(String msg){
        super(msg);
    }
}
