package com.example.ecomproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SignInDto {

    private String userName;
    private String password;
}
