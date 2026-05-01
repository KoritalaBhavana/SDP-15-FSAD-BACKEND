package com.travelnestpro.repository;

import com.travelnestpro.entity.Homestay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HomestayRepository extends JpaRepository<Homestay, Long> {
    List<Homestay> findByIsAvailableTrue();
    List<Homestay> findByIsApprovedTrue();
    List<Homestay> findByIsApprovedTrueAndIsAvailableTrue();
    Optional<Homestay> findByIdAndIsApprovedTrueAndIsAvailableTrue(Long id);
    List<Homestay> findByHostId(Long hostId);
    List<Homestay> findByCityContainingIgnoreCaseAndIsApprovedTrueAndIsAvailableTrue(String city);
}
