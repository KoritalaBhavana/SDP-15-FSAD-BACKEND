package com.travelnestpro.controller;

import com.travelnestpro.entity.Wishlist;
import com.travelnestpro.service.WishlistService;
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
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Operation(summary = "Add to wishlist")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Added")})
    @PostMapping
    public ResponseEntity<Wishlist> add(@Valid @RequestBody Map<String, Long> payload) {
        return ResponseEntity.ok(wishlistService.add(payload.get("touristId"), payload.get("homestayId")));
    }

    @Operation(summary = "Remove from wishlist")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Deleted")})
    @DeleteMapping("/{touristId}/{homestayId}")
    public ResponseEntity<Void> remove(@PathVariable Long touristId, @PathVariable Long homestayId) {
        wishlistService.remove(touristId, homestayId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get wishlist by tourist")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{touristId}")
    public ResponseEntity<List<Wishlist>> getByTourist(@PathVariable Long touristId) {
        return ResponseEntity.ok(wishlistService.getByTourist(touristId));
    }

    @Operation(summary = "Check if wishlisted")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/check/{touristId}/{homestayId}")
    public ResponseEntity<Map<String, Boolean>> check(@PathVariable Long touristId, @PathVariable Long homestayId) {
        return ResponseEntity.ok(Map.of("wishlisted", wishlistService.isWishlisted(touristId, homestayId)));
    }
}
