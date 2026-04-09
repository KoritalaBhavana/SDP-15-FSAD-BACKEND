package com.travelnestpro.controller;

import com.travelnestpro.dto.ItineraryRequest;
import com.travelnestpro.entity.Itinerary;
import com.travelnestpro.service.ItineraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itineraries")
@Tag(name = "Itineraries")
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

    @Operation(summary = "Create itinerary")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Created")})
    @PostMapping
    public ResponseEntity<Itinerary> create(@Valid @RequestBody ItineraryRequest request) {
        return ResponseEntity.ok(itineraryService.create(request));
    }

    @Operation(summary = "Get all itineraries")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping
    public ResponseEntity<List<Itinerary>> getAll() {
        return ResponseEntity.ok(itineraryService.getAll());
    }

    @Operation(summary = "Get itineraries by guide")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/guide/{guideId}")
    public ResponseEntity<List<Itinerary>> getByGuide(@PathVariable Long guideId) {
        return ResponseEntity.ok(itineraryService.getByGuide(guideId));
    }

    @Operation(summary = "Update itinerary")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Updated")})
    @PutMapping("/{id}")
    public ResponseEntity<Itinerary> update(@PathVariable Long id, @Valid @RequestBody ItineraryRequest request) {
        return ResponseEntity.ok(itineraryService.update(id, request));
    }

    @Operation(summary = "Delete itinerary")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Deleted")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itineraryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
