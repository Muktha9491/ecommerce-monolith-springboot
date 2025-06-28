package com.ecommerce.service;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // This method is called by Spring Security during authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // You may treat "username" as "email" in your case
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),  // username
                user.getPassword(),  // password
                new ArrayList<>()  // authorities (empty for now)
        );
    }
}
