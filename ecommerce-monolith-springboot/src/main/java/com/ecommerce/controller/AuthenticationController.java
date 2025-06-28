package com.ecommerce.controller;

import com.ecommerce.dto.AuthRequest;
import com.ecommerce.dto.AuthResponse;
import com.ecommerce.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        try {
            // Authenticate using email and password
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
                )
            );

            // Load user details and generate JWT
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            String token = jwtUtil.generateToken(userDetails.getUsername());

            return new AuthResponse(token);

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
