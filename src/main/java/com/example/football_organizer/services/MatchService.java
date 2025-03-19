package com.example.football_organizer.services;

import com.example.football_organizer.dtos.MatchDto;
import com.example.football_organizer.entity.Match;
import com.example.football_organizer.entity.Participation;
import com.example.football_organizer.entity.User;
import com.example.football_organizer.repo.IMatchRepository;
import com.example.football_organizer.repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class MatchService {
    @Autowired
    private IMatchRepository matchRepository;
    @Autowired
    private IUserRepository userRepository;

    public MatchDto createMatch (MatchDto matchDto , Authentication authentication){
        String username= authentication.getName();
        Long userId= userRepository.findByUserName(username).get().getId();

        User organizer = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Organizer not found"));
        Match match= new Match();
        match.setDate(matchDto.getDate());
        match.setLocation(matchDto.getLocation());
        match.setMaxPlayer(matchDto.getMaxPlayer());
        match.setOrganizer(organizer);
        match.setLatitude(matchDto.getLatitude());
        match.setLongitude(matchDto.getLongitude());
        match.setStatus("OPEN");
        Match saved = matchRepository.save(match);


        MatchDto result = new MatchDto();
        result.setId(saved.getId());
        result.setDate(saved.getDate());
        result.setLocation(saved.getLocation());
        result.setMaxPlayer(saved.getMaxPlayer());
        result.setStatus(saved.getStatus());
        result.setLatitude(saved.getLatitude());
        result.setLongitude(saved.getLongitude());
        result.setOrganizerId(saved.getOrganizer().getId());
        return result;
    }

    public List<MatchDto> getAllMatch(){
        return matchRepository.findAll().stream()
                .map(match->{
                    MatchDto dto= new MatchDto();
                    dto.setId(match.getId());
                    dto.setDate(match.getDate());
                    dto.setLocation(match.getLocation());
                    dto.setMaxPlayer(match.getMaxPlayer());
                    dto.setOrganizerId(match.getOrganizer().getId());
                    dto.setLatitude(match.getLatitude());
                    dto.setLongitude(match.getLongitude());
                    dto.setStatus(match.getStatus());
                    return dto;
                }).collect(Collectors.toList());
    }


    public int getConfirmedPlayer(Long matchId){
        Match match = matchRepository.findById(matchId)
                .orElseThrow(()-> new RuntimeException("Match not Found"));
        return (int)match.getParticipants().stream()
                .filter(Participation::getConfirmed)
                .count();
    }


    public List<MatchDto> findNearByMatch(double userLat, double userLon, double radiusKm){
        List<Match> allMatches = matchRepository.findAll();
        return allMatches.stream()
                .filter(match -> calculateDistance(userLat,userLon,match.getLatitude(),match.getLongitude())<= radiusKm)
                .map(match -> {
                    MatchDto result= new MatchDto();
                    result.setId(match.getId());
                    result.setDate(match.getDate());
                    result.setLocation(match.getLocation());
                    result.setMaxPlayer(match.getMaxPlayer());
                    result.setStatus(match.getStatus());
                    result.setOrganizerId(match.getOrganizer().getId());
                    result.setLatitude(match.getLatitude());
                    result.setLongitude(match.getLongitude());
                    return result;
                }).collect(Collectors.toList());
    }

    private double calculateDistance(double lat1 ,double lon1 , double lat2 , double lon2){
        final int R= 6371;
        double latDistance = Math.toRadians(lat2-lat1);
        double lonDistance = Math.toRadians(lon2 -lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

}
