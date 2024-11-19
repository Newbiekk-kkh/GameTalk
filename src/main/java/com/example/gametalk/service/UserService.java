package com.example.gametalk.service;

import com.example.gametalk.config.PasswordEncoder;
import com.example.gametalk.dto.UserResponseDto;
import com.example.gametalk.entity.User;
import com.example.gametalk.exception.authentication.AuthenticationErrorCode;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.exception.validation.ValidationErrorCode;
import com.example.gametalk.exception.validation.ValidationException;
import com.example.gametalk.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto signUp(String email, String password, String name) throws ValidationException {
        //이메일 중복 체크
        if (userRepository.existsByEmail(email)) {
            throw new ValidationException(ValidationErrorCode.EMAIL_DUPLICATED);
        }
        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        //유저 생성 및 저장
        User createdUser = new User(email, encodedPassword, name);
        userRepository.save(createdUser);

        return new UserResponseDto(createdUser.getEmail(), createdUser.getUsername());
    }

    public String authenticate(String email, String password) throws AuthenticationException {
        //이메일 체크
        User findUser = userRepository.findByEmail(email).orElseThrow(() ->
                new AuthenticationException(AuthenticationErrorCode.EMAIL_INCORRECT)
        );

        //비밀번호 체크
        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new AuthenticationException(AuthenticationErrorCode.PASSWORD_INCORRECT);
        }

        return "로그인 완료";
    }
}
