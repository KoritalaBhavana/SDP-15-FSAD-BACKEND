package com.travelnestpro.service;

import com.travelnestpro.dto.HomestayRequest;
import com.travelnestpro.entity.Homestay;
import com.travelnestpro.entity.User;
import com.travelnestpro.exception.BadRequestException;
import com.travelnestpro.exception.ResourceNotFoundException;
import com.travelnestpro.repository.HomestayRepository;
import com.travelnestpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HomestayService {

    @Autowired
    private HomestayRepository homestayRepository;

    @Autowired
    private UserRepository userRepository;

    public Homestay create(HomestayRequest request, String username) {
        User host = userRepository.findByEmail(username)
                .orElseThrow(() -> new BadRequestException("Signed-in user not found"));

        return createForHost(request, host);
    }

    public Homestay createFromForm(
            String title,
            String description,
            String location,
            String city,
            String state,
            String category,
            BigDecimal pricePerNight,
            Integer maxGuests,
            String amenities,
            String imageUrl,
            String imageUrls,
            String distanceInfo,
            String username
    ) {
        User host = userRepository.findByEmail(username)
                .orElseThrow(() -> new BadRequestException("Signed-in user not found"));

        HomestayRequest request = new HomestayRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setLocation(defaultText(location, "India"));
        request.setCity(defaultText(city, request.getLocation()));
        request.setState(state);
        request.setCategory(defaultText(category, "Homestay"));
        request.setPricePerNight(pricePerNight == null ? BigDecimal.ZERO : pricePerNight);
        request.setMaxGuests(maxGuests == null ? 1 : maxGuests);
        request.setAmenities(defaultText(amenities, "WiFi,Home Food"));
        request.setImageUrl(imageUrl);
        request.setImageUrls(imageUrls);
        request.setDistanceInfo(defaultText(distanceInfo, "New listing"));

        return createForHost(request, host);
    }

    private String defaultText(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    private Homestay createForHost(HomestayRequest request, User host) {
        Homestay homestay = new Homestay();
        homestay.setHostId(host.getId());
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
        homestay.setImageUrls(request.getImageUrls());
        homestay.setDistanceInfo(request.getDistanceInfo());
        homestay.setRating(BigDecimal.ZERO);
        homestay.setReviewCount(0);
        homestay.setIsAvailable(true);
        homestay.setIsApproved(true);
        return homestayRepository.save(homestay);
    }

    public List<Homestay> getAll() {
        return homestayRepository.findByIsApprovedTrueAndIsAvailableTrue();
    }

    public Homestay getById(Long id) {
        return homestayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Homestay not found with id: " + id));
    }

    public Homestay getAvailableById(Long id) {
        return homestayRepository.findByIdAndIsApprovedTrueAndIsAvailableTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
    }

    public List<Homestay> getByHost(Long hostId) {
        return homestayRepository.findByHostId(hostId);
    }

    public Homestay update(Long id, HomestayRequest request) {
        Homestay existing = getById(id);
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
        existing.setImageUrls(request.getImageUrls());
        existing.setDistanceInfo(request.getDistanceInfo());
        return homestayRepository.save(existing);
    }

    public void delete(Long id) {
        Homestay homestay = getById(id);
        homestayRepository.delete(homestay);
    }

    public List<Homestay> searchByCity(String city) {
        return homestayRepository.findByCityContainingIgnoreCaseAndIsApprovedTrueAndIsAvailableTrue(city);
    }

    public Homestay toggleAvailability(Long id) {
        Homestay homestay = getById(id);
        homestay.setIsAvailable(!Boolean.TRUE.equals(homestay.getIsAvailable()));
        return homestayRepository.save(homestay);
    }

    public Homestay approve(Long id) {
        Homestay homestay = getById(id);
        homestay.setIsApproved(true);
        homestay.setIsAvailable(true);
        return homestayRepository.save(homestay);
    }
}
