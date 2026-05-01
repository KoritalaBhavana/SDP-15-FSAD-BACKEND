package com.travelnestpro.security;

import com.travelnestpro.entity.User;
import com.travelnestpro.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApprovalGateFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getRequestURI();
        if (!path.startsWith("/api/") || isAlwaysAllowed(request) || isAdmin(authentication)) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (user == null || "TOURIST".equalsIgnoreCase(user.getRole()) || user.getIsApproved()) {
            filterChain.doFilter(request, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Account is waiting for admin approval");
    }

    private boolean isAlwaysAllowed(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        return path.startsWith("/api/auth/")
                || ("GET".equalsIgnoreCase(method) && (path.startsWith("/api/homestays") || path.startsWith("/api/properties")))
                || path.startsWith("/api-docs/")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/uploads/");
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }
}
