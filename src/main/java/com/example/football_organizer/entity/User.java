package com.example.football_organizer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;


import java.util.List;

@Entity
@Data

@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    private String userName;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "Skill level cannot be empty")
    private String skillLevel;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    private String roles;

    @OneToMany(mappedBy = "organizer")
//    @JsonManagedReference
//    @JsonIgnore
    private List<Match> organizedMatches;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    @JsonIgnore
    private List<Participation> participations;
}
