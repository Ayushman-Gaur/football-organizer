package com.example.football_organizer.repo;

import com.example.football_organizer.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IParticipationRepository extends JpaRepository<Participation,Long> {
}
