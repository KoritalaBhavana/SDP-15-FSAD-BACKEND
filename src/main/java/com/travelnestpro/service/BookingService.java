package com.travelnestpro.service;

import com.travelnestpro.dto.BookingRequest;
import com.travelnestpro.entity.Booking;
import com.travelnestpro.entity.Homestay;
import com.travelnestpro.exception.BadRequestException;
import com.travelnestpro.exception.ResourceNotFoundException;
import com.travelnestpro.repository.BookingRepository;
import com.travelnestpro.repository.HomestayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private HomestayRepository homestayRepository;

    public Booking create(BookingRequest request) {
        Homestay homestay = homestayRepository.findById(request.getHomestayId())
                .orElseThrow(() -> new ResourceNotFoundException("Homestay not found with id: " + request.getHomestayId()));

        long nights = ChronoUnit.DAYS.between(request.getCheckIn(), request.getCheckOut());
        if (nights <= 0) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }

        BigDecimal totalAmount = homestay.getPricePerNight()
                .multiply(BigDecimal.valueOf(nights))
                .multiply(new BigDecimal("1.18"))
                .setScale(2, RoundingMode.HALF_UP);

        Booking booking = new Booking();
        booking.setHomestayId(request.getHomestayId());
        booking.setTouristId(request.getTouristId());
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());
        booking.setGuests(request.getGuests());
        booking.setTotalAmount(totalAmount);
        booking.setStatus("PENDING");
        booking.setSpecialRequests(request.getSpecialRequests());

        return bookingRepository.save(booking);
    }

    public List<Booking> getByTourist(Long touristId) {
        return bookingRepository.findByTouristId(touristId);
    }

    public List<Booking> getByHost(Long hostId) {
        List<Long> homestayIds = homestayRepository.findByHostId(hostId)
                .stream()
                .map(Homestay::getId)
                .toList();
        if (homestayIds.isEmpty()) {
            return List.of();
        }
        return bookingRepository.findByHomestayIdIn(homestayIds);
    }

    public Booking getById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    public Booking updateStatus(Long id, String status) {
        Booking booking = getById(id);
        booking.setStatus(status.toUpperCase());
        return bookingRepository.save(booking);
    }

    public BigDecimal getTotalEarnings(Long hostId) {
        return getByHost(hostId).stream()
                .filter(booking -> "CONFIRMED".equals(booking.getStatus()) || "COMPLETED".equals(booking.getStatus()))
                .map(Booking::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
