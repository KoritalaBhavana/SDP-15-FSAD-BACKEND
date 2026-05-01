package com.travelnestpro.config;

import com.travelnestpro.entity.Attraction;
import com.travelnestpro.entity.Booking;
import com.travelnestpro.entity.DiningBooking;
import com.travelnestpro.entity.GuideBooking;
import com.travelnestpro.entity.Homestay;
import com.travelnestpro.entity.Itinerary;
import com.travelnestpro.entity.User;
import com.travelnestpro.repository.AttractionRepository;
import com.travelnestpro.repository.BookingRepository;
import com.travelnestpro.repository.DiningBookingRepository;
import com.travelnestpro.repository.GuideBookingRepository;
import com.travelnestpro.repository.HomestayRepository;
import com.travelnestpro.repository.ItineraryRepository;
import com.travelnestpro.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            UserService userService,
            HomestayRepository homestayRepository,
            BookingRepository bookingRepository,
            AttractionRepository attractionRepository,
            ItineraryRepository itineraryRepository,
            GuideBookingRepository guideBookingRepository,
            DiningBookingRepository diningBookingRepository
    ) {
        return args -> {
            userService.ensureLocalUser("Admin", "admin@travelnest.com", "admin123", "ADMIN", "APPROVED");
            User tourist = userService.ensureLocalUser("Aarav Sharma", "tourist@travelnest.com", "tourist123", "TOURIST", "APPROVED");
            User host = userService.ensureLocalUser("Priya Host", "host@travelnest.com", "host123", "HOST", "APPROVED");
            User guide = userService.ensureLocalUser("Karan Guide", "guide@travelnest.com", "guide123", "GUIDE", "APPROVED");
            User chef = userService.ensureLocalUser("Neha Chef", "chef@travelnest.com", "chef123", "CHEF", "APPROVED");

            if (homestayRepository.count() == 0) {
                Homestay manaliStay = new Homestay();
                manaliStay.setHostId(host.getId());
                manaliStay.setTitle("Mountain Dew Cottage");
                manaliStay.setDescription("A live Spring Boot-backed sample homestay for the TravelNestPro frontend.");
                manaliStay.setLocation("Manali, Himachal Pradesh");
                manaliStay.setCity("Manali");
                manaliStay.setState("Himachal Pradesh");
                manaliStay.setCategory("Nature Stay");
                manaliStay.setPricePerNight(new BigDecimal("2200"));
                manaliStay.setMaxGuests(4);
                manaliStay.setAmenities("WiFi,Home Food,Parking,Mountain View");
                manaliStay.setImageUrl("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=600&q=80");
                manaliStay.setRating(new BigDecimal("4.8"));
                manaliStay.setReviewCount(34);
                manaliStay.setDistanceInfo("2.3 km from Mall Road");
                manaliStay.setIsAvailable(true);
                manaliStay.setIsApproved(true);
                homestayRepository.save(manaliStay);

                Homestay goaStay = new Homestay();
                goaStay.setHostId(host.getId());
                goaStay.setTitle("Beachside Paradise");
                goaStay.setDescription("Sample luxury stay wired to the backend API.");
                goaStay.setLocation("Goa");
                goaStay.setCity("Goa");
                goaStay.setState("Goa");
                goaStay.setCategory("Luxury");
                goaStay.setPricePerNight(new BigDecimal("3500"));
                goaStay.setMaxGuests(6);
                goaStay.setAmenities("WiFi,Pool,Breakfast,Beach Access");
                goaStay.setImageUrl("https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=600&q=80");
                goaStay.setRating(new BigDecimal("4.9"));
                goaStay.setReviewCount(21);
                goaStay.setDistanceInfo("300 m from Beach");
                goaStay.setIsAvailable(true);
                goaStay.setIsApproved(true);
                homestayRepository.save(goaStay);
            }

            homestayRepository.findAll().forEach(homestay -> {
                boolean changed = false;
                if (!Boolean.TRUE.equals(homestay.getIsApproved())) {
                    homestay.setIsApproved(true);
                    changed = true;
                }
                if (homestay.getIsAvailable() == null) {
                    homestay.setIsAvailable(true);
                    changed = true;
                }
                if (changed) {
                    homestayRepository.save(homestay);
                }
            });

            Homestay sampleStay = homestayRepository.findAll().stream().findFirst().orElse(null);
            if (sampleStay != null && bookingRepository.count() == 0) {
                Booking booking = new Booking();
                booking.setHomestayId(sampleStay.getId());
                booking.setTouristId(tourist.getId());
                booking.setCheckIn(LocalDate.now().plusDays(10));
                booking.setCheckOut(LocalDate.now().plusDays(13));
                booking.setGuests(2);
                booking.setTotalAmount(new BigDecimal("6600"));
                booking.setStatus("CONFIRMED");
                booking.setSpecialRequests("Mountain view room preferred");
                bookingRepository.save(booking);
            }

            if (attractionRepository.count() == 0) {
                Attraction attraction = new Attraction();
                attraction.setName("Rohtang Pass");
                attraction.setDescription("Backend seeded attraction for the guide and tourist dashboards.");
                attraction.setLocation("Manali");
                attraction.setCity("Manali");
                attraction.setCategory("Adventure");
                attraction.setImageUrl("https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=600&q=80");
                attraction.setEntryFee(BigDecimal.ZERO);
                attraction.setBestTime("May - October");
                attraction.setDistanceKm(new BigDecimal("51"));
                attraction.setRating(new BigDecimal("4.8"));
                attraction.setAddedBy(guide.getId());
                attractionRepository.save(attraction);
            }

            if (itineraryRepository.count() == 0) {
                Itinerary itinerary = new Itinerary();
                itinerary.setGuideId(guide.getId());
                itinerary.setTitle("3 Days in Manali");
                itinerary.setDescription("Guided trekking, sightseeing, and local food trail.");
                itinerary.setDurationDays(3);
                itinerary.setPlaces("Mall Road,Hadimba Temple,Rohtang Pass");
                itinerary.setPrice(new BigDecimal("4500"));
                itineraryRepository.save(itinerary);
            }

            if (guideBookingRepository.count() == 0) {
                GuideBooking guideBooking = new GuideBooking();
                guideBooking.setTouristId(tourist.getId());
                guideBooking.setGuideId(guide.getId());
                guideBooking.setBookingDate(LocalDate.now().plusDays(14));
                guideBooking.setDurationDays(2);
                guideBooking.setActivityType("Sightseeing");
                guideBooking.setTotalAmount(new BigDecimal("3000"));
                guideBooking.setStatus("PENDING");
                guideBookingRepository.save(guideBooking);
            }

            if (diningBookingRepository.count() == 0) {
                DiningBooking diningBooking = new DiningBooking();
                diningBooking.setTouristId(tourist.getId());
                diningBooking.setChefId(chef.getId());
                diningBooking.setRestaurantName("TravelNest Dining");
                diningBooking.setBookingDate(LocalDate.now().plusDays(11));
                diningBooking.setBookingTime("Dinner");
                diningBooking.setGuests(2);
                diningBooking.setType("CHEF");
                diningBooking.setStatus("PENDING");
                diningBooking.setTotalAmount(new BigDecimal("1800"));
                diningBookingRepository.save(diningBooking);
            }
        };
    }
}
