package com.example.football_organizer.controller;

import com.example.football_organizer.dtos.MatchDto;
import com.example.football_organizer.entity.Match;
import com.example.football_organizer.services.MatchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@Validated
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<MatchDto> createMatch(@Valid @RequestBody MatchDto matchDto , Authentication authentication){
        MatchDto createdMatch = matchService.createMatch(matchDto,authentication);
        return ResponseEntity.ok(createdMatch);
    }
    @GetMapping("/*")
    public ResponseEntity<List<MatchDto>> getAllMatches(){
        return ResponseEntity.ok(matchService.getAllMatch());
    }
    @GetMapping("/{matchId}/confirmed-players")
    public ResponseEntity<Integer> getConfirmedPlayer(@NotNull (message = "Match id cannnot be empty") @PathVariable Long matchId){
        return ResponseEntity.ok(matchService.getConfirmedPlayer(matchId));
    }
    @GetMapping("/nearby")
    public ResponseEntity<List<MatchDto>> getNearByMatch(@NotNull(message = "Latitude is required") @RequestParam Double latitude,
                                                   @NotNull(message = "Longitude is required") @RequestParam Double longitude,
                                                   @RequestParam(defaultValue = "10") double radiusKm){
        return ResponseEntity.ok(matchService.findNearByMatch(latitude,longitude,radiusKm));
    }
}
