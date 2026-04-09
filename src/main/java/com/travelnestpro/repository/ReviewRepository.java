package com.travelnestpro.repository;

import com.travelnestpro.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTargetIdAndTargetType(Long targetId, String targetType);
    List<Review> findByUserId(Long userId);
}
