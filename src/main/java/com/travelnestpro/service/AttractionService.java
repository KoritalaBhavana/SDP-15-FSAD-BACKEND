package com.travelnestpro.service;

import com.travelnestpro.entity.Attraction;
import com.travelnestpro.exception.ResourceNotFoundException;
import com.travelnestpro.repository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionService {

    @Autowired
    private AttractionRepository attractionRepository;

    public Attraction create(Attraction attraction) {
        return attractionRepository.save(attraction);
    }

    public List<Attraction> getAll() {
        return attractionRepository.findAll();
    }

    public Attraction getById(Long id) {
        return attractionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attraction not found with id: " + id));
    }

    public List<Attraction> searchByCity(String city) {
        return attractionRepository.findByCityContainingIgnoreCase(city);
    }

    public List<Attraction> getByGuide(Long guideId) {
        return attractionRepository.findByAddedBy(guideId);
    }

    public Attraction update(Long id, Attraction attraction) {
        Attraction existing = getById(id);
        existing.setName(attraction.getName());
        existing.setDescription(attraction.getDescription());
        existing.setLocation(attraction.getLocation());
        existing.setCity(attraction.getCity());
        existing.setCategory(attraction.getCategory());
        existing.setImageUrl(attraction.getImageUrl());
        existing.setEntryFee(attraction.getEntryFee());
        existing.setBestTime(attraction.getBestTime());
        existing.setDistanceKm(attraction.getDistanceKm());
        existing.setRating(attraction.getRating());
        return attractionRepository.save(existing);
    }

    public void delete(Long id) {
        attractionRepository.delete(getById(id));
    }
}
