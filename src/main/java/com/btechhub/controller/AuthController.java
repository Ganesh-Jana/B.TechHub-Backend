package com.btechhub.controller;

import com.btechhub.entity.User;
import com.btechhub.repository.UserRepository;
import com.btechhub.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body) {
        try {
            Map<String, Object> result = authService.signup(
                    body.get("name"),
                    body.get("email"),
                    body.get("password")
            );
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            Map<String, Object> result = authService.login(
                    body.get("email"),
                    body.get("password")
            );
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Returns current user info including role — used by frontend to check admin access
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        return userRepository.findByEmail(userDetails.getUsername())
                .<ResponseEntity<?>>map(user -> ResponseEntity.ok(Map.of(
                        "id",    user.getId(),
                        "name",  user.getName(),
                        "email", user.getEmail(),
                        "role",  user.getRole() != null ? user.getRole() : "USER"
                )))
                .orElse(ResponseEntity.status(404).body(Map.of("error", "User not found")));
    }
}