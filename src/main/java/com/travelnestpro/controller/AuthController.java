package com.travelnestpro.controller;

import com.travelnestpro.dto.LoginRequest;
import com.travelnestpro.dto.RegisterRequest;
import com.travelnestpro.dto.AuthResponse;
import com.travelnestpro.entity.User;
import com.travelnestpro.exception.BadRequestException;
import com.travelnestpro.service.JwtService;
import com.travelnestpro.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Operation(summary = "Register user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return ResponseEntity.ok(buildAuthResponse(user));
    }

    @Operation(summary = "Login user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logged in"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.login(request);
        return ResponseEntity.ok(buildAuthResponse(user));
    }

    @Operation(summary = "Google OAuth login/register")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Google auth success"),
            @ApiResponse(responseCode = "400", description = "Invalid google token")
    })
    @PostMapping("/google")
    public ResponseEntity<AuthResponse> googleAuth(@Valid @RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String role = payload.getOrDefault("role", "TOURIST");

        if (token == null || token.isBlank()) {
            throw new BadRequestException("Google token is required");
        }

        RestTemplate restTemplate = new RestTemplate();

        @SuppressWarnings("unchecked")
        Map<String, Object> googleResponse;
        try {
            googleResponse = restTemplate.getForObject("https://oauth2.googleapis.com/tokeninfo?access_token=" + token, Map.class);
        } catch (Exception ex) {
            googleResponse = restTemplate.getForObject("https://oauth2.googleapis.com/tokeninfo?id_token=" + token, Map.class);
        }

        if (googleResponse == null || googleResponse.get("email") == null) {
            throw new BadRequestException("Unable to verify Google token");
        }

        String email = String.valueOf(googleResponse.get("email"));
        String name = String.valueOf(googleResponse.getOrDefault("name", email.split("@")[0]));
        String picture = String.valueOf(googleResponse.getOrDefault("picture", ""));

        User user = userService.findOrCreateGoogleUser(email, name, picture, role);
        return ResponseEntity.ok(buildAuthResponse(user));
    }

    private AuthResponse buildAuthResponse(User user) {
        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getProfileImage(),
                user.getStatus(),
                jwtService.generateToken(user.getEmail(), user.getRole(), user.getId())
        );
    }
}
