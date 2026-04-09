package com.travelnestpro.controller;

import com.travelnestpro.dto.GuideBookingRequest;
import com.travelnestpro.entity.GuideBooking;
import com.travelnestpro.service.GuideBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/guide-bookings")
@Tag(name = "Guide Bookings")
public class GuideBookingController {

    @Autowired
    private GuideBookingService guideBookingService;

    @Operation(summary = "Create guide booking")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Created")})
    @PostMapping
    public ResponseEntity<GuideBooking> create(@Valid @RequestBody GuideBookingRequest request) {
        return ResponseEntity.ok(guideBookingService.create(request));
    }

    @Operation(summary = "Get guide bookings by tourist")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/tourist/{touristId}")
    public ResponseEntity<List<GuideBooking>> getByTourist(@PathVariable Long touristId) {
        return ResponseEntity.ok(guideBookingService.getByTourist(touristId));
    }

    @Operation(summary = "Get guide bookings by guide")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/guide/{guideId}")
    public ResponseEntity<List<GuideBooking>> getByGuide(@PathVariable Long guideId) {
        return ResponseEntity.ok(guideBookingService.getByGuide(guideId));
    }

    @Operation(summary = "Update guide booking status")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Updated")})
    @PutMapping("/{id}/status")
    public ResponseEntity<GuideBooking> updateStatus(@PathVariable Long id, @Valid @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(guideBookingService.updateStatus(id, payload.get("status")));
    }

    @Operation(summary = "Get guide earnings")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/guide/{guideId}/earnings")
    public ResponseEntity<BigDecimal> getEarnings(@PathVariable Long guideId) {
        return ResponseEntity.ok(guideBookingService.getEarnings(guideId));
    }
}
