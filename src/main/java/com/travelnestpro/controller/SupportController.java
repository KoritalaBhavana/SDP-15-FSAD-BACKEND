package com.travelnestpro.controller;

import com.travelnestpro.dto.ContactEmailRequest;
import com.travelnestpro.service.SupportMailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    private final SupportMailService supportMailService;

    public SupportController(SupportMailService supportMailService) {
        this.supportMailService = supportMailService;
    }

    @PostMapping("/contact")
    public ResponseEntity<Map<String, String>> contact(@Valid @RequestBody ContactEmailRequest request) {
        supportMailService.sendContactEmail(request);
        return ResponseEntity.ok(Map.of("message", "Support request submitted successfully"));
    }
}
