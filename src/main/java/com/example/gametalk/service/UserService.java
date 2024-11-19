package com.example.gametalk.service;

import com.example.gametalk.dto.UserRequestDto;
import com.example.gametalk.dto.UserResponseDto;
import com.example.gametalk.entity.User;
import com.example.gametalk.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto signUp(String email, String password, String name) {
        User createdUser = new User(email, password, name);
        userRepository.save(createdUser);
        return new UserResponseDto(createdUser.getEmail(), createdUser.getUsername());
    }
}
