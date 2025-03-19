package com.example.football_organizer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonBackReference
//    @JsonIgnore
    private User user;



    @ManyToOne
    @JoinColumn(name = "match_id")
//    @JsonBackReference
//    @JsonIgnore
    private Match match;

    private Boolean confirmed;


}
