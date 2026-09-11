package com.makers.gestionprestamos.controller;

import com.makers.gestionprestamos.entity.User;
import com.makers.gestionprestamos.repository.UserRepository;
import com.makers.gestionprestamos.security.JwtService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Set<String> REVOKED_TOKENS = ConcurrentHashMap.newKeySet();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            if (!authentication.isAuthenticated()) {
                throw new BadCredentialsException("Credenciales inválidas");
            }
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(Map.of("message", "Credenciales inválidas"));
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "role", user.getRole().name()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            REVOKED_TOKENS.add(authHeader.substring(7));
        }
        return ResponseEntity.ok(Map.of("message", "Logout exitoso"));
    }

    public static boolean isRevoked(String token) {
        return REVOKED_TOKENS.contains(token);
    }

    public record LoginRequest(
            @NotBlank String email,
            @NotBlank String password
    ) {}
}
