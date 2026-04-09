package com.travelnestpro.service;

import com.travelnestpro.entity.Wishlist;
import com.travelnestpro.exception.ResourceNotFoundException;
import com.travelnestpro.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public Wishlist add(Long touristId, Long homestayId) {
        if (wishlistRepository.existsByTouristIdAndHomestayId(touristId, homestayId)) {
            return wishlistRepository.findByTouristIdAndHomestayId(touristId, homestayId)
                    .orElseThrow(() -> new ResourceNotFoundException("Wishlist entry not found"));
        }
        Wishlist wishlist = new Wishlist();
        wishlist.setTouristId(touristId);
        wishlist.setHomestayId(homestayId);
        return wishlistRepository.save(wishlist);
    }

    public void remove(Long touristId, Long homestayId) {
        Wishlist wishlist = wishlistRepository.findByTouristIdAndHomestayId(touristId, homestayId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist entry not found"));
        wishlistRepository.delete(wishlist);
    }

    public List<Wishlist> getByTourist(Long touristId) {
        return wishlistRepository.findByTouristId(touristId);
    }

    public boolean isWishlisted(Long touristId, Long homestayId) {
        return wishlistRepository.existsByTouristIdAndHomestayId(touristId, homestayId);
    }
}
