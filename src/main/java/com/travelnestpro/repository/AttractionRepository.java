package com.travelnestpro.repository;

import com.travelnestpro.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    List<Attraction> findByCityContainingIgnoreCase(String city);
    List<Attraction> findByAddedBy(Long userId);
}
