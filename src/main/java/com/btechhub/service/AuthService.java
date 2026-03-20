package com.btechhub.service;

import com.btechhub.entity.User;
import com.btechhub.repository.UserRepository;
import com.btechhub.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public Map<String, Object> signup(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role("USER")          // all new signups are regular users
                .build();
        user = userRepository.save(user);

        // @JsonIgnore on password means it won't appear in response
        String token = jwtUtil.generateToken(user.getEmail());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);   // password field hidden by @JsonIgnore
        return response;
    }

    public Map<String, Object> login(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);   // password field hidden by @JsonIgnore
        return response;
    }
}
