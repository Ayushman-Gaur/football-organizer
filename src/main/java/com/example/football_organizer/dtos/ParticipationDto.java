package com.example.football_organizer.dtos;

import lombok.Data;

@Data
public class ParticipationDto {
    private Long id;
    private Long userId;
    private Long matchId;
    private boolean confirmed;
    private String emailStatus;
}
