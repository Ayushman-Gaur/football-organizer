package com.example.football_organizer.repo;

import com.example.football_organizer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);
}
