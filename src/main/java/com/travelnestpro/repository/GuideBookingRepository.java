package com.travelnestpro.repository;

import com.travelnestpro.entity.GuideBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideBookingRepository extends JpaRepository<GuideBooking, Long> {
    List<GuideBooking> findByTouristId(Long touristId);
    List<GuideBooking> findByGuideId(Long guideId);
}
