package com.travelnestpro.service;

import com.travelnestpro.dto.HomestayRequest;
import com.travelnestpro.entity.Homestay;
import com.travelnestpro.exception.ResourceNotFoundException;
import com.travelnestpro.repository.HomestayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HomestayService {

    @Autowired
    private HomestayRepository homestayRepository;

    public Homestay create(HomestayRequest request) {
        Homestay homestay = new Homestay();
        homestay.setHostId(request.getHostId());
        homestay.setTitle(request.getTitle());
        homestay.setDescription(request.getDescription());
        homestay.setLocation(request.getLocation());
        homestay.setCity(request.getCity());
        homestay.setState(request.getState());
        homestay.setCategory(request.getCategory());
        homestay.setPricePerNight(request.getPricePerNight());
        homestay.setMaxGuests(request.getMaxGuests());
        homestay.setAmenities(request.getAmenities());
        homestay.setImageUrl(request.getImageUrl());
        homestay.setDistanceInfo(request.getDistanceInfo());
        homestay.setRating(BigDecimal.ZERO);
        homestay.setReviewCount(0);
        homestay.setIsAvailable(true);
        return homestayRepository.save(homestay);
    }

    public List<Homestay> getAll() {
        return homestayRepository.findByIsAvailableTrue();
    }

    public Homestay getById(Long id) {
        return homestayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Homestay not found with id: " + id));
    }

    public List<Homestay> getByHost(Long hostId) {
        return homestayRepository.findByHostId(hostId);
    }

    public Homestay update(Long id, HomestayRequest request) {
        Homestay existing = getById(id);
        existing.setHostId(request.getHostId());
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setLocation(request.getLocation());
        existing.setCity(request.getCity());
        existing.setState(request.getState());
        existing.setCategory(request.getCategory());
        existing.setPricePerNight(request.getPricePerNight());
        existing.setMaxGuests(request.getMaxGuests());
        existing.setAmenities(request.getAmenities());
        existing.setImageUrl(request.getImageUrl());
        existing.setDistanceInfo(request.getDistanceInfo());
        return homestayRepository.save(existing);
    }

    public void delete(Long id) {
        Homestay homestay = getById(id);
        homestayRepository.delete(homestay);
    }

    public List<Homestay> searchByCity(String city) {
        return homestayRepository.findByCityContainingIgnoreCaseAndIsAvailableTrue(city);
    }

    public Homestay toggleAvailability(Long id) {
        Homestay homestay = getById(id);
        homestay.setIsAvailable(!Boolean.TRUE.equals(homestay.getIsAvailable()));
        return homestayRepository.save(homestay);
    }
}
