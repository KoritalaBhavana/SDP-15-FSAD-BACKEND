package com.travelnestpro.repository;

import com.travelnestpro.entity.Homestay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomestayRepository extends JpaRepository<Homestay, Long> {
    List<Homestay> findByIsAvailableTrue();
    List<Homestay> findByHostId(Long hostId);
    List<Homestay> findByCityContainingIgnoreCaseAndIsAvailableTrue(String city);
}
