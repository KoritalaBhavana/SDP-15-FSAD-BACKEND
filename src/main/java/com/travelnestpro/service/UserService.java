package com.travelnestpro.service;

import com.travelnestpro.dto.LoginRequest;
import com.travelnestpro.dto.RegisterRequest;
import com.travelnestpro.entity.User;
import com.travelnestpro.exception.BadRequestException;
import com.travelnestpro.exception.ResourceNotFoundException;
import com.travelnestpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    private static final Set<String> ALLOWED_ROLES = Set.of("TOURIST", "HOST", "GUIDE", "ADMIN", "CHEF");
    private static final Set<String> ALLOWED_STATUSES = Set.of("PENDING", "APPROVED", "REJECTED");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new BadRequestException("Email already registered");
        });

        String normalizedRole = normalizeRole(request.getRole());
        if ("ADMIN".equals(normalizedRole)) {
            throw new BadRequestException("Admin accounts cannot be created from public registration.");
        }
        boolean approved = "TOURIST".equals(normalizedRole);
        String status = approved ? "APPROVED" : "PENDING";

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(normalizedRole);
        user.setPhone(request.getPhone());
        user.setStatus(status);
        user.setApproved(approved);
        user.setOnboardingCompleted(true);
        user.setIsNew(false);
        user.setVerified(approved);
        user.setAuthProvider("LOCAL");

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }
        if ("REJECTED".equalsIgnoreCase(user.getStatus())) {
            throw new BadRequestException("Your account was rejected by the admin.");
        }
        return user;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User updateUser(Long id, Map<String, Object> updates) {
        User user = getUserById(id);

        updates.remove("id");
        updates.remove("createdAt");
        updates.remove("password");

        updates.forEach((key, value) -> {
            try {
                Field field = User.class.getDeclaredField(key);
                field.setAccessible(true);
                if ("role".equals(key) && value instanceof String stringValue) {
                    field.set(user, normalizeRole(stringValue));
                    return;
                }
                if ("status".equals(key) && value instanceof String stringValue) {
                    field.set(user, normalizeStatus(stringValue));
                    return;
                }
                field.set(user, value);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
        });

        return userRepository.save(user);
    }

    public User updateProfileImage(Long id, String imageUrl) {
        User user = getUserById(id);
        user.setProfileImage(imageUrl);
        return userRepository.save(user);
    }

    public List<User> getAllByRole(String role) {
        return userRepository.findByRole(normalizeRole(role));
    }

    public List<User> getPendingUsers() {
        return userRepository.findAll().stream()
                .filter(user -> !"TOURIST".equalsIgnoreCase(user.getRole()))
                .filter(user -> !user.getIsApproved())
                .toList();
    }

    public User updateStatus(Long id, String status) {
        User user = getUserById(id);
        String normalizedStatus = normalizeStatus(status);
        user.setStatus(normalizedStatus);
        boolean approved = "APPROVED".equalsIgnoreCase(normalizedStatus);
        user.setApproved(approved);
        user.setVerified(approved);
        user.setIsNew(!approved);
        return userRepository.save(user);
    }

    public User verifyUser(Long id) {
        return updateStatus(id, "APPROVED");
    }

    public User requestVerification(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));
        user.setOnboardingCompleted(true);
        user.setApproved(false);
        user.setVerified(false);
        user.setStatus("PENDING");
        user.setIsNew(false);
        return userRepository.save(user);
    }

    public void rejectUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("tourists", userRepository.countByRole("TOURIST"));
        stats.put("hosts", userRepository.countByRole("HOST"));
        stats.put("guides", userRepository.countByRole("GUIDE"));
        stats.put("chefs", userRepository.countByRole("CHEF"));
        long pendingInterviews = userRepository.findAll().stream()
                .filter(user -> ("HOST".equals(user.getRole()) || "GUIDE".equals(user.getRole()) || "CHEF".equals(user.getRole()))
                        && !user.getIsApproved())
                .count();
        stats.put("pendingInterviews", pendingInterviews);
        return stats;
    }

    public User findOrCreateGoogleUser(String email, String name, String picture, String role) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("GOOGLE_AUTH_" + email));
            String normalizedRole = normalizeRole(role);
            boolean approved = "TOURIST".equals(normalizedRole);
            user.setRole(normalizedRole);
            user.setStatus(approved ? "APPROVED" : "PENDING");
            user.setApproved(approved);
            user.setOnboardingCompleted(true);
            user.setVerified(approved);
            user.setIsNew(false);
            user.setAuthProvider("GOOGLE");
            user.setProfileImage(picture);
            return userRepository.save(user);
        });
    }

    public User ensureLocalUser(String name, String email, String rawPassword, String role, String status) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(normalizeRole(role));
            user.setStatus(normalizeStatus(status));
            boolean approved = "APPROVED".equalsIgnoreCase(status);
            user.setApproved(approved);
            user.setOnboardingCompleted(approved);
            user.setVerified(approved);
            user.setIsNew(!approved);
            user.setAuthProvider("LOCAL");
            return userRepository.save(user);
        });
    }

    private String normalizeRole(String role) {
        String normalizedRole = role == null ? "" : role.trim().toUpperCase();
        if (!ALLOWED_ROLES.contains(normalizedRole)) {
            throw new BadRequestException("Invalid role supplied");
        }
        return normalizedRole;
    }

    private String normalizeStatus(String status) {
        String normalizedStatus = status == null ? "" : status.trim().toUpperCase();
        if (!ALLOWED_STATUSES.contains(normalizedStatus)) {
            throw new BadRequestException("Invalid status supplied");
        }
        return normalizedStatus;
    }
}
