package com.travelnestpro.controller;

import com.travelnestpro.entity.Booking;
import com.travelnestpro.entity.Homestay;
import com.travelnestpro.entity.User;
import com.travelnestpro.repository.BookingRepository;
import com.travelnestpro.repository.HomestayRepository;
import com.travelnestpro.repository.ReviewRepository;
import com.travelnestpro.repository.UserRepository;
import com.travelnestpro.service.HomestayService;
import com.travelnestpro.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HomestayRepository homestayRepository;

    @Autowired
    private HomestayService homestayService;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/pending-users")
    public ResponseEntity<List<User>> getPendingUsers() {
        return ResponseEntity.ok(userService.getPendingUsers());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> dashboard() {
        List<User> allUsers = userRepository.findAll();
        List<User> pendingUsers = allUsers.stream()
                .filter(user -> !"TOURIST".equalsIgnoreCase(user.getRole()))
                .filter(user -> !user.getIsApproved())
                .toList();
        List<User> approvedUsers = allUsers.stream().filter(User::getIsApproved).toList();
        List<User> rejectedUsers = allUsers.stream().filter(user -> "REJECTED".equalsIgnoreCase(user.getStatus())).toList();
        long activeListings = homestayRepository.findByIsApprovedTrueAndIsAvailableTrue().size();
        long totalBookings = bookingRepository.count();

        return ResponseEntity.ok(new AdminDashboardResponse(
                allUsers.size(),
                totalBookings,
                activeListings,
                userService.getStats().getOrDefault("pendingInterviews", 0L),
                pendingUsers,
                approvedUsers,
                rejectedUsers,
                bookingRepository.findAll().stream().map(Booking::getStatus).toList(),
                homestayRepository.findAll().stream().map(Homestay::getTitle).toList(),
                reviewRepository.count()
        ));
    }

    @PutMapping("/approve-user/{id}")
    public ResponseEntity<User> approveUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.updateStatus(id, "APPROVED"));
    }

    @PutMapping("/approve-property/{id}")
    public ResponseEntity<Homestay> approveProperty(@PathVariable Long id) {
        return ResponseEntity.ok(homestayService.approve(id));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<User> approve(@PathVariable Long id) {
        return ResponseEntity.ok(userService.verifyUser(id));
    }

    @PutMapping("/verify/{id}")
    public ResponseEntity<User> verifyUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.verifyUser(id));
    }

    @PutMapping("/reject-user/{id}")
    public ResponseEntity<User> rejectUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.updateStatus(id, "REJECTED"));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Void> rejectAndDeleteUser(@PathVariable Long id) {
        userService.rejectUser(id);
        return ResponseEntity.noContent().build();
    }

    public record AdminDashboardResponse(
            long totalUsers,
            long totalBookings,
            long activeListings,
            long pendingInterviews,
            List<User> pendingUsers,
            List<User> approvedUsers,
            List<User> rejectedUsers,
            List<String> bookingStatuses,
            List<String> activeListingTitles,
            long totalReviews
    ) {
    }
}
