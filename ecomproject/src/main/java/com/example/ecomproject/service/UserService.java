package com.example.ecomproject.service;

import com.example.ecomproject.common.ApiResponse;
import com.example.ecomproject.model.User;
import com.example.ecomproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public ApiResponse signUp(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        ApiResponse response=new ApiResponse(true, "sucessfully registered");
        return response;
    }
}
