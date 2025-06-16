package com.example.ecomproject.controller;

import com.example.ecomproject.common.ApiResponse;
import com.example.ecomproject.dto.SignInDto;
import com.example.ecomproject.model.User;
import com.example.ecomproject.service.JwtService;
import com.example.ecomproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody User user){
        ApiResponse response=userService.signUp(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signIn(@RequestBody SignInDto signInDto){
        Authentication authentication=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signInDto.getUserName(),signInDto.getPassword()));
        if(authentication.isAuthenticated()){
            String token=jwtService.generateToken(signInDto.getUserName());
            return new ResponseEntity<>(new ApiResponse(true,"login successful "+token),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, "invalid credentials"),HttpStatus.NOT_FOUND);
    }

}
