package com.example.football_organizer.services;

import com.example.football_organizer.dtos.ParticipationDto;
import com.example.football_organizer.entity.Match;
import com.example.football_organizer.entity.Participation;
import com.example.football_organizer.entity.User;
import com.example.football_organizer.repo.IMatchRepository;
import com.example.football_organizer.repo.IParticipationRepository;
import com.example.football_organizer.repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ParticipationService {
    @Autowired
    private IParticipationRepository participationRepository;
    @Autowired
    private IMatchRepository matchRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public ParticipationDto joinMatch(Long matchId, Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User not authenticated");
        }
        String username= authentication.getName();
//        logger.info("User {} attempting to join match {}", username, matchId);
        User user= userRepository.findByUserName(username)
                .orElseThrow(()-> new RuntimeException("User Not Found"));
        Match match = matchRepository.findById(matchId)
                .orElseThrow(()-> new RuntimeException("Match Not Found"));
        if(match.getParticipants().size()>=match.getMaxPlayer()){
            throw new RuntimeException("Match is full");
        }
        Participation participation = new Participation();
        participation.setUser(user);
        participation.setMatch(match);
        participation.setConfirmed(false);//confirm later
        Participation saved= participationRepository.save(participation);
        String emailStatus="SENT";


        try {
            emailService.sendEmail(
                    user.getEmail(),
                    "You have Joined",
                    "Hi"+user.getUserName()+",\n\n you have joined match at"+ match.getLocation()+
                            "on "+ match.getDate() + ". Please confirm your participation soon!\n\nCheers,\nFootball Organiser Team"
            );
        } catch (Exception e) {
            emailStatus="FAILED";
        }

        ParticipationDto result = new ParticipationDto();
        result.setId(saved.getId());
        result.setUserId(saved.getUser().getId());
        result.setMatchId(saved.getMatch().getId());
        result.setConfirmed(saved.getConfirmed());
        result.setEmailStatus(emailStatus);
        return result;
    }


    //This is the method for confirming the player
    public ParticipationDto confirmParticipation(Long participationId , Long userId){
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(()-> new RuntimeException("participation Not Found"));

        if(!participation.getUser().getId().equals(userId)){
            throw  new RuntimeException("You can Only Confirm your own Participation");
        }
        if(participation.getConfirmed().equals(true)){
            throw new RuntimeException("Participation id already Confirmed");
        }
        participation.setConfirmed(true);
        Participation saved= participationRepository.save(participation);

        String emailStatus="SENT";

        try {
            emailService.sendEmail(
                    participation.getUser().getEmail(),
                    "Participation Confirmed!",
                    "Hi "+ participation.getUser().getUserName()+ ",\n\nYou’ve confirmed your spot in the match at " +
                            participation.getMatch().getLocation()+" on " +participation.getMatch().getDate() +
                            ". See you there\\n\\nCheers,\\nFootball Organizer Team"
            );
        } catch (Exception e) {
            emailStatus="FAILED";
        }


        ParticipationDto result = new ParticipationDto();
        result.setId(saved.getId());
        result.setUserId(saved.getUser().getId());
        result.setMatchId(saved.getMatch().getId());
        result.setConfirmed(saved.getConfirmed());
        result.setEmailStatus(emailStatus);
        return result;
    }
}
