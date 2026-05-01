package com.travelnestpro.controller;

import com.travelnestpro.entity.Booking;
import com.travelnestpro.entity.Homestay;
import com.travelnestpro.entity.Review;
import com.travelnestpro.entity.User;
import com.travelnestpro.repository.BookingRepository;
import com.travelnestpro.repository.HomestayRepository;
import com.travelnestpro.repository.ReviewRepository;
import com.travelnestpro.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/host")
@Tag(name = "Host")
public class HostController {

    @Autowired
    private HomestayRepository homestayRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ReviewRepository reviewRepository;

        @Autowired
        private UserRepository userRepository;

    @GetMapping("/dashboard/{hostId}")
    public ResponseEntity<HostDashboardResponse> dashboard(@PathVariable Long hostId) {
                return ResponseEntity.ok(buildDashboard(hostId));
        }

        @GetMapping("/dashboard")
        public ResponseEntity<HostDashboardResponse> dashboard(Authentication authentication) {
                User host = userRepository.findByEmail(authentication.getName()).orElse(null);
                if (host == null) {
                        return ResponseEntity.ok(new HostDashboardResponse(BigDecimal.ZERO, 0, 0, BigDecimal.ZERO, 0, BigDecimal.ZERO, List.of(), List.of()));
                }
                return ResponseEntity.ok(buildDashboard(host.getId()));
        }

        private HostDashboardResponse buildDashboard(Long hostId) {
        List<Homestay> properties = homestayRepository.findByHostId(hostId);
        List<Long> homestayIds = properties.stream().map(Homestay::getId).toList();
        List<Booking> bookings = homestayIds.isEmpty() ? List.of() : bookingRepository.findByHomestayIdIn(homestayIds);
        List<Review> hostReviews = homestayIds.isEmpty()
                ? Collections.emptyList()
                : homestayIds.stream()
                .flatMap(homestayId -> reviewRepository.findByTargetIdAndTargetType(homestayId, "HOMESTAY").stream())
                .toList();

        BigDecimal totalEarnings = bookings.stream()
                .filter(booking -> "CONFIRMED".equalsIgnoreCase(booking.getStatus()) || "COMPLETED".equalsIgnoreCase(booking.getStatus()))
                .map(booking -> booking.getTotalAmount() == null ? BigDecimal.ZERO : booking.getTotalAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long activeBookings = bookings.stream().filter(booking -> "PENDING".equalsIgnoreCase(booking.getStatus()) || "CONFIRMED".equalsIgnoreCase(booking.getStatus())).count();
        long totalReviews = hostReviews.size();
        BigDecimal occupancyRate = properties.isEmpty()
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(activeBookings).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(properties.size() * 4L), 2, RoundingMode.HALF_UP);
        BigDecimal averageRating = hostReviews.isEmpty()
                ? BigDecimal.ZERO
                : hostReviews.stream()
                .map(review -> review.getRating() == null ? BigDecimal.ZERO : BigDecimal.valueOf(review.getRating()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(hostReviews.size()), 2, RoundingMode.HALF_UP);

        return new HostDashboardResponse(
                totalEarnings,
                activeBookings,
                totalReviews,
                occupancyRate,
                properties.size(),
                averageRating,
                properties,
                bookings
        );
    }

    public record HostDashboardResponse(
            BigDecimal totalEarnings,
            long activeBookings,
            long reviews,
            BigDecimal occupancyRate,
            long totalProperties,
            BigDecimal averageRating,
            List<Homestay> properties,
            List<Booking> bookings
    ) {
    }
}