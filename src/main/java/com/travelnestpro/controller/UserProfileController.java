package com.travelnestpro.controller;

import com.travelnestpro.entity.User;
import com.travelnestpro.service.FileStorageService;
import com.travelnestpro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/request-verification")
    public ResponseEntity<User> requestVerification(Authentication authentication) {
        return ResponseEntity.ok(userService.requestVerification(authentication.getName()));
    }

    @PutMapping(value = "/update-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> updateProfile(
            @RequestParam Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar
    ) {
        Map<String, Object> updates = new HashMap<>();
        if (name != null) {
            updates.put("name", name);
        }
        if (email != null) {
            updates.put("email", email);
        }
        if (avatar != null && !avatar.isEmpty()) {
            updates.put("profileImage", fileStorageService.store(avatar).fileUrl());
        }

        return ResponseEntity.ok(userService.updateUser(id, updates));
    }
}
