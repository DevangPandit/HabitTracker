package com.ht.HabitTracker.Controller;

import com.ht.HabitTracker.DTO.LoginRequest;
import com.ht.HabitTracker.Model.User;
import com.ht.HabitTracker.Repository.UserRepository;
import com.ht.HabitTracker.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final JwtUtil jwtUtil;
    @Autowired
    @Lazy // Delay injection of AuthenticationProvider
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private UserRepository userRepository;

    public AuthController(@Lazy AuthenticationProvider authenticationProvider, JwtUtil jwtUtil) {
        this.authenticationProvider = authenticationProvider;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Received username: " + loginRequest.getUsername());
        System.out.println("Received password: " + loginRequest.getPassword());
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        Long id = user.get().getId();
        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", loginRequest.getUsername());
            // Add token expiration time
            response.put("userId", id);

            // Return the response as JSON
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);        }
    }
}