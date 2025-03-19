package com.example.football_organizer.repo;

import com.example.football_organizer.entity.Match;
import com.example.football_organizer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMatchRepository extends JpaRepository<Match,Long> {
}
