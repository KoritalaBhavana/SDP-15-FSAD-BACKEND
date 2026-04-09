package com.travelnestpro.controller;

import com.travelnestpro.dto.HomestayRequest;
import com.travelnestpro.entity.Homestay;
import com.travelnestpro.service.HomestayService;
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
@RequestMapping("/api/homestays")
@Tag(name = "Homestays")
public class HomestayController {

    @Autowired
    private HomestayService homestayService;

    @Operation(summary = "Create homestay")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Created")})
    @PostMapping
    public ResponseEntity<Homestay> create(@Valid @RequestBody HomestayRequest request) {
        return ResponseEntity.ok(homestayService.create(request));
    }

    @Operation(summary = "Get all homestays")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping
    public ResponseEntity<List<Homestay>> getAll() {
        return ResponseEntity.ok(homestayService.getAll());
    }

    @Operation(summary = "Get homestay by id")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{id}")
    public ResponseEntity<Homestay> getById(@PathVariable Long id) {
        return ResponseEntity.ok(homestayService.getById(id));
    }

    @Operation(summary = "Get homestays by host")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<Homestay>> getByHost(@PathVariable Long hostId) {
        return ResponseEntity.ok(homestayService.getByHost(hostId));
    }

    @Operation(summary = "Update homestay")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Updated")})
    @PutMapping("/{id}")
    public ResponseEntity<Homestay> update(@PathVariable Long id, @Valid @RequestBody HomestayRequest request) {
        return ResponseEntity.ok(homestayService.update(id, request));
    }

    @Operation(summary = "Delete homestay")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Deleted")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        homestayService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search homestays by city")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/search")
    public ResponseEntity<List<Homestay>> search(@RequestParam String city) {
        return ResponseEntity.ok(homestayService.searchByCity(city));
    }

    @Operation(summary = "Toggle homestay availability")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Updated")})
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Homestay> toggle(@PathVariable Long id) {
        return ResponseEntity.ok(homestayService.toggleAvailability(id));
    }
}
