package com.travelnestpro.service;

import com.travelnestpro.dto.GuideBookingRequest;
import com.travelnestpro.entity.GuideBooking;
import com.travelnestpro.exception.ResourceNotFoundException;
import com.travelnestpro.repository.GuideBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GuideBookingService {

    @Autowired
    private GuideBookingRepository guideBookingRepository;

    public GuideBooking create(GuideBookingRequest request) {
        GuideBooking booking = new GuideBooking();
        booking.setTouristId(request.getTouristId());
        booking.setGuideId(request.getGuideId());
        booking.setBookingDate(request.getBookingDate());
        booking.setDurationDays(request.getDurationDays());
        booking.setActivityType(request.getActivityType());
        booking.setTotalAmount(request.getTotalAmount());
        booking.setStatus("PENDING");
        booking.setSpecialRequests(request.getSpecialRequests());
        return guideBookingRepository.save(booking);
    }

    public List<GuideBooking> getByTourist(Long touristId) {
        return guideBookingRepository.findByTouristId(touristId);
    }

    public List<GuideBooking> getByGuide(Long guideId) {
        return guideBookingRepository.findByGuideId(guideId);
    }

    public GuideBooking updateStatus(Long id, String status) {
        GuideBooking booking = guideBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guide booking not found with id: " + id));
        booking.setStatus(status.toUpperCase());
        return guideBookingRepository.save(booking);
    }

    public BigDecimal getEarnings(Long guideId) {
        return guideBookingRepository.findByGuideId(guideId).stream()
                .filter(booking -> "CONFIRMED".equals(booking.getStatus()) || "COMPLETED".equals(booking.getStatus()))
                .map(GuideBooking::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
