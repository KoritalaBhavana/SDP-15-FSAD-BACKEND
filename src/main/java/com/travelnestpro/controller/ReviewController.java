package com.travelnestpro.controller;

import com.travelnestpro.dto.ReviewRequest;
import com.travelnestpro.entity.Review;
import com.travelnestpro.service.ReviewService;
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
@RequestMapping("/api/reviews")
@Tag(name = "Reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Operation(summary = "Add review")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Created")})
    @PostMapping
    public ResponseEntity<Review> add(@Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.add(request));
    }

    @Operation(summary = "Get reviews by target")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{targetType}/{targetId}")
    public ResponseEntity<List<Review>> getByTarget(@PathVariable String targetType, @PathVariable Long targetId) {
        return ResponseEntity.ok(reviewService.getByTarget(targetId, targetType));
    }

    @Operation(summary = "Reply to review")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Updated")})
    @PutMapping("/{id}/reply")
    public ResponseEntity<Review> addReply(@PathVariable Long id, @Valid @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(reviewService.addReply(id, payload.get("reply")));
    }

    @Operation(summary = "Get reviews by user")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getByUser(userId));
    }
}
