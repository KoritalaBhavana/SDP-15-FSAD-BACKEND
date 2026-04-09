package com.travelnestpro.repository;

import com.travelnestpro.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
    List<Itinerary> findByGuideId(Long guideId);
}
