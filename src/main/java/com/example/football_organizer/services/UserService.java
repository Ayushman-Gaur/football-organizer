package com.example.football_organizer.services;

import com.example.football_organizer.dtos.UserDto;
import com.example.football_organizer.entity.User;
import com.example.football_organizer.repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto createUser(UserDto userDto){
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setSkillLevel(userDto.getSkillLevel());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));// Hash the password
        user.setRoles(userDto.getRoles()!= null?userDto.getRoles():"USER");
        User saved = userRepository.save(user);

        UserDto result = new UserDto();
        result.setId(saved.getId());
        result.setUserName(saved.getUserName());
        result.setEmail(saved.getEmail());
        result.setSkillLevel(saved.getSkillLevel());
        return result;
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not Found"));
    }
}
