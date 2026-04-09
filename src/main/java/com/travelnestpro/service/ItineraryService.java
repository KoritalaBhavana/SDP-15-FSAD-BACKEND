package com.travelnestpro.service;

import com.travelnestpro.dto.ItineraryRequest;
import com.travelnestpro.entity.Itinerary;
import com.travelnestpro.exception.ResourceNotFoundException;
import com.travelnestpro.repository.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    public Itinerary create(ItineraryRequest request) {
        Itinerary itinerary = new Itinerary();
        itinerary.setGuideId(request.getGuideId());
        itinerary.setTitle(request.getTitle());
        itinerary.setDescription(request.getDescription());
        itinerary.setDurationDays(request.getDurationDays());
        itinerary.setPlaces(request.getPlaces());
        itinerary.setPrice(request.getPrice());
        return itineraryRepository.save(itinerary);
    }

    public List<Itinerary> getAll() {
        return itineraryRepository.findAll();
    }

    public List<Itinerary> getByGuide(Long guideId) {
        return itineraryRepository.findByGuideId(guideId);
    }

    public Itinerary update(Long id, ItineraryRequest request) {
        Itinerary itinerary = itineraryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Itinerary not found with id: " + id));
        itinerary.setTitle(request.getTitle());
        itinerary.setDescription(request.getDescription());
        itinerary.setDurationDays(request.getDurationDays());
        itinerary.setPlaces(request.getPlaces());
        itinerary.setPrice(request.getPrice());
        return itineraryRepository.save(itinerary);
    }

    public void delete(Long id) {
        Itinerary itinerary = itineraryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Itinerary not found with id: " + id));
        itineraryRepository.delete(itinerary);
    }
}
