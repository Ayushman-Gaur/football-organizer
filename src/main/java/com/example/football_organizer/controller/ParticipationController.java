package com.example.football_organizer.controller;

import com.example.football_organizer.dtos.ParticipationDto;
import com.example.football_organizer.entity.Participation;
import com.example.football_organizer.services.ParticipationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participations")
@Validated
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;
    @PostMapping("/join")
    public ResponseEntity<ParticipationDto> joinMatch(@NotNull(message = "Match ID cannot be null") @RequestParam Long matchId , Authentication authentication){
        if(authentication==null || !authentication.isAuthenticated()){
            throw new AccessDeniedException("User not authenticated");
        }
        return ResponseEntity.ok(participationService.joinMatch(matchId, authentication));
    }

    @PostMapping("/confirm")
    public ResponseEntity<ParticipationDto> confirmedParticipation(@NotNull(message = "Participation Id cannot be empty")@RequestParam Long participationId,
                                                                @NotNull(message="User Id cannot be empty")@RequestParam Long userId){
        return ResponseEntity.ok((participationService.confirmParticipation(participationId, userId)));
    }

}
