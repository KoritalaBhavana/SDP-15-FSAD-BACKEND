package com.travelnestpro.controller;

import com.travelnestpro.entity.Attraction;
import com.travelnestpro.service.AttractionService;
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
@RequestMapping("/api/attractions")
@Tag(name = "Attractions")
public class AttractionController {

    @Autowired
    private AttractionService attractionService;

    @Operation(summary = "Create attraction")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Created")})
    @PostMapping
    public ResponseEntity<Attraction> create(@Valid @RequestBody Attraction attraction) {
        return ResponseEntity.ok(attractionService.create(attraction));
    }

    @Operation(summary = "Get all attractions")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping
    public ResponseEntity<List<Attraction>> getAll() {
        return ResponseEntity.ok(attractionService.getAll());
    }

    @Operation(summary = "Get attraction by id")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{id}")
    public ResponseEntity<Attraction> getById(@PathVariable Long id) {
        return ResponseEntity.ok(attractionService.getById(id));
    }

    @Operation(summary = "Search attractions by city")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/search")
    public ResponseEntity<List<Attraction>> searchByCity(@RequestParam String city) {
        return ResponseEntity.ok(attractionService.searchByCity(city));
    }

    @Operation(summary = "Get attractions by guide")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/guide/{guideId}")
    public ResponseEntity<List<Attraction>> getByGuide(@PathVariable Long guideId) {
        return ResponseEntity.ok(attractionService.getByGuide(guideId));
    }

    @Operation(summary = "Delete attraction")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Deleted")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        attractionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
