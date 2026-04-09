package com.travelnestpro.controller;

import com.travelnestpro.entity.User;
import com.travelnestpro.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get user by id")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Update user")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Updated")})
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(userService.updateUser(id, updates));
    }

    @Operation(summary = "Update user profile image")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Updated")})
    @PutMapping("/{id}/image")
    public ResponseEntity<User> updateImage(@PathVariable Long id, @Valid @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(userService.updateProfileImage(id, payload.get("imageUrl")));
    }

    @Operation(summary = "Get users by role")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getByRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.getAllByRole(role));
    }

    @Operation(summary = "Get pending users")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/pending")
    public ResponseEntity<List<User>> getPending() {
        return ResponseEntity.ok(userService.getPendingUsers());
    }

    @Operation(summary = "Update user status")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Updated")})
    @PutMapping("/{id}/status")
    public ResponseEntity<User> updateStatus(@PathVariable Long id, @Valid @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(userService.updateStatus(id, payload.get("status")));
    }

    @Operation(summary = "Get user stats")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(userService.getStats());
    }
}
