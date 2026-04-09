package com.travelnestpro.repository;

import com.travelnestpro.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTouristId(Long touristId);
    List<Booking> findByHomestayId(Long homestayId);
    List<Booking> findByHomestayIdIn(List<Long> ids);
    List<Booking> findByStatus(String status);
}
