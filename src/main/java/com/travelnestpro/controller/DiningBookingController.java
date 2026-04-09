package com.travelnestpro.controller;

import com.travelnestpro.dto.DiningBookingRequest;
import com.travelnestpro.entity.DiningBooking;
import com.travelnestpro.service.DiningBookingService;
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
@RequestMapping("/api/dining-bookings")
@Tag(name = "Dining Bookings")
public class DiningBookingController {

    @Autowired
    private DiningBookingService diningBookingService;

    @Operation(summary = "Create dining booking")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Created")})
    @PostMapping
    public ResponseEntity<DiningBooking> create(@Valid @RequestBody DiningBookingRequest request) {
        return ResponseEntity.ok(diningBookingService.create(request));
    }

    @Operation(summary = "Get dining bookings by tourist")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/tourist/{touristId}")
    public ResponseEntity<List<DiningBooking>> getByTourist(@PathVariable Long touristId) {
        return ResponseEntity.ok(diningBookingService.getByTourist(touristId));
    }

    @Operation(summary = "Get dining bookings by chef")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/chef/{chefId}")
    public ResponseEntity<List<DiningBooking>> getByChef(@PathVariable Long chefId) {
        return ResponseEntity.ok(diningBookingService.getByChef(chefId));
    }

    @Operation(summary = "Update dining booking status")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Updated")})
    @PutMapping("/{id}/status")
    public ResponseEntity<DiningBooking> updateStatus(@PathVariable Long id, @Valid @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(diningBookingService.updateStatus(id, payload.get("status")));
    }
}
