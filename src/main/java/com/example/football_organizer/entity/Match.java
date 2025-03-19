package com.example.football_organizer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data

public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date cannot be empty")
    @Future(message = "Date must be in the future")
    private LocalDateTime date;

    @NotNull(message = "Location cannot be empty")
    private String Location;

    @Positive(message = "Max player cannot be negative")
    private int maxPlayer;


    private String status; // OPEN , FULL, CLOSED


    @ManyToOne
    @JoinColumn(name = "organizer_id")
//    @JsonBackReference
//    @JsonIgnore
    private User organizer;

    @OneToMany(mappedBy = "match" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    @JsonIgnore
    private List<Participation> participants;


    private double latitude;
    private double longitude;

}
