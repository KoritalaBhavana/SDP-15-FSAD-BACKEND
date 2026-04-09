package com.travelnestpro.controller;

import com.travelnestpro.dto.BookingRequest;
import com.travelnestpro.entity.Booking;
import com.travelnestpro.service.BookingService;
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
@RequestMapping("/api/bookings")
@Tag(name = "Bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Create booking")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Created")})
    @PostMapping
    public ResponseEntity<Booking> create(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.create(request));
    }

    @Operation(summary = "Get bookings by tourist")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/tourist/{touristId}")
    public ResponseEntity<List<Booking>> getByTourist(@PathVariable Long touristId) {
        return ResponseEntity.ok(bookingService.getByTourist(touristId));
    }

    @Operation(summary = "Get bookings by host")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<Booking>> getByHost(@PathVariable Long hostId) {
        return ResponseEntity.ok(bookingService.getByHost(hostId));
    }

    @Operation(summary = "Get booking by id")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getById(id));
    }

    @Operation(summary = "Update booking status")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Updated")})
    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateStatus(@PathVariable Long id, @Valid @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(bookingService.updateStatus(id, payload.get("status")));
    }

    @Operation(summary = "Get host earnings")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/host/{hostId}/earnings")
    public ResponseEntity<BigDecimal> getEarnings(@PathVariable Long hostId) {
        return ResponseEntity.ok(bookingService.getTotalEarnings(hostId));
    }
}
