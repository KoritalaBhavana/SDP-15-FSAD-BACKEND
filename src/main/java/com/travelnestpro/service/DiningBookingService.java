package com.travelnestpro.service;

import com.travelnestpro.dto.DiningBookingRequest;
import com.travelnestpro.entity.DiningBooking;
import com.travelnestpro.exception.ResourceNotFoundException;
import com.travelnestpro.repository.DiningBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiningBookingService {

    @Autowired
    private DiningBookingRepository diningBookingRepository;

    public DiningBooking create(DiningBookingRequest request) {
        DiningBooking booking = new DiningBooking();
        booking.setTouristId(request.getTouristId());
        booking.setChefId(request.getChefId());
        booking.setRestaurantName(request.getRestaurantName());
        booking.setBookingDate(request.getBookingDate());
        booking.setBookingTime(request.getBookingTime());
        booking.setGuests(request.getGuests());
        booking.setTableNumber(request.getTableNumber());
        booking.setType(request.getType() == null ? "TABLE" : request.getType());
        booking.setStatus("PENDING");
        booking.setSpecialRequests(request.getSpecialRequests());
        booking.setTotalAmount(request.getTotalAmount());
        return diningBookingRepository.save(booking);
    }

    public List<DiningBooking> getByTourist(Long touristId) {
        return diningBookingRepository.findByTouristId(touristId);
    }

    public List<DiningBooking> getByChef(Long chefId) {
        return diningBookingRepository.findByChefId(chefId);
    }

    public DiningBooking updateStatus(Long id, String status) {
        DiningBooking booking = diningBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dining booking not found with id: " + id));
        booking.setStatus(status.toUpperCase());
        return diningBookingRepository.save(booking);
    }
}
