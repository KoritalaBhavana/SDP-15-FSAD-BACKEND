package com.travelnestpro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ItineraryRequest {

    @NotNull
    private Long guideId;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    @Positive
    private Integer durationDays;

    @NotBlank
    private String places;

    @NotNull
    @Positive
    private BigDecimal price;

    public Long getGuideId() { return guideId; }
    public void setGuideId(Long guideId) { this.guideId = guideId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }
    public String getPlaces() { return places; }
    public void setPlaces(String places) { this.places = places; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
