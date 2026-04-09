package com.travelnestpro.service;

import com.travelnestpro.dto.ReviewRequest;
import com.travelnestpro.entity.Review;
import com.travelnestpro.exception.ResourceNotFoundException;
import com.travelnestpro.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review add(ReviewRequest request) {
        Review review = new Review();
        review.setUserId(request.getUserId());
        review.setTargetId(request.getTargetId());
        review.setTargetType(request.getTargetType());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return reviewRepository.save(review);
    }

    public List<Review> getByTarget(Long targetId, String targetType) {
        return reviewRepository.findByTargetIdAndTargetType(targetId, targetType);
    }

    public Review addReply(Long id, String reply) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        review.setReply(reply);
        return reviewRepository.save(review);
    }

    public List<Review> getByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
}
