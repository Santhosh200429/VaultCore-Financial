
package com.zaalima.vaultcore.controller;

import com.zaalima.vaultcore.config.JwtUtil;
import com.zaalima.vaultcore.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        if (!"user@vaultcore.com".equals(request.getEmail())) {
            return ResponseEntity.status(401).build();
        }
        String token = jwtUtil.generateToken(request.getEmail());
        return ResponseEntity.ok(Map.of("accessToken", token));
    }
}
