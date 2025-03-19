package com.example.football_organizer.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    @NotBlank(message = "Username cannot be blank")
    private String userName;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "Skill level cannot be blank")
    private String skillLevel;
    private String password;
    private String roles;

}
