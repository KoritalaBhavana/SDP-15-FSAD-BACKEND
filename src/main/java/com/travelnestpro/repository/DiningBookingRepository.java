package com.travelnestpro.repository;

import com.travelnestpro.entity.DiningBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiningBookingRepository extends JpaRepository<DiningBooking, Long> {
    List<DiningBooking> findByTouristId(Long touristId);
    List<DiningBooking> findByChefId(Long chefId);
}
