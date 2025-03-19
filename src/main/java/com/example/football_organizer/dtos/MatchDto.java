package com.example.football_organizer.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchDto {

    private Long id;
    @NotNull(message = "Date cannot be null")
    @Future(message = "Date should be in future")
    private LocalDateTime date;
    @NotBlank(message = "Location cannot be empty")
    private String location;
    @Positive(message = "Max Player cannot be negative")
    private int maxPlayer;
    private String status;
    private Long organizerId;

    @NotNull(message = "Latitude is required")
    private double latitude;
    @NotNull(message = "Longitude is required")
    private double longitude;
}
