package com.example.gametalk.service;

import com.example.gametalk.dto.users.UserUpdateResponseDto;
import com.example.gametalk.dto.users.UserProfileResponseDto;
import com.example.gametalk.entity.User;
import com.example.gametalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.gametalk.config.PasswordEncoder;
import com.example.gametalk.dto.users.UserResponseDto;
import com.example.gametalk.exception.authentication.AuthenticationErrorCode;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.exception.validation.ValidationErrorCode;
import com.example.gametalk.exception.validation.ValidationException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import java.util.Map;
import java.util.Optional;


@Getter
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto signUp(String email, String password, String name) throws ValidationException {

        Optional<User> findUser = userRepository.findByEmail(email);

        if (findUser.isPresent()) {
            User user = findUser.get();

            //탈퇴여부 확인
            if (!user.isActivated()) {
                throw new ValidationException(ValidationErrorCode.EMAIL_BANNED);
            }

            //이메일 중복 확인
            throw new ValidationException(ValidationErrorCode.EMAIL_DUPLICATED);
        }

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        //유저 생성 및 저장
        User createdUser = new User(email, encodedPassword, name, true);
        userRepository.save(createdUser);

        return new UserResponseDto(createdUser.getEmail(), createdUser.getUsername());
    }

    public String authenticate(String email, String password) throws AuthenticationException {
        //이메일 체크
        User findUser = userRepository.findByEmail(email).orElseThrow(() ->
                new AuthenticationException(AuthenticationErrorCode.EMAIL_INCORRECT)
        );

        checkPassword(password, findUser);

        return "로그인 완료";
    }

    @Transactional
    public String deactivateAccount(Long id, String password) throws AuthenticationException {

        User findUser = userRepository.findById(id).orElseThrow(() -> new AuthenticationException(AuthenticationErrorCode.USER_DEACTIVATED));

        checkPassword(password, findUser);

        findUser.patchActivateStatus(false);

        userRepository.save(findUser);

        return "회원탈퇴 완료";
    }

    // 비밀번호 체크
    private void checkPassword(String password, User findUser) throws AuthenticationException {
        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new AuthenticationException(AuthenticationErrorCode.PASSWORD_INCORRECT);
        }
    }

    // 프로필 조회 기능
    public UserProfileResponseDto findUserById(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);
        return User.toDto(user);
    }

    // 프로필 수정 기능
    public UserUpdateResponseDto updateUser(Long userId, Map<String, Object> updates) {
        User findUser = userRepository.findByIdOrElseThrow(userId);

        updates.forEach((key, value) -> {
            switch (key) {
                case "username":
                    findUser.setUsername((String) value);
                    break;
                case "email":
                    findUser.setEmail((String) value);
                    break;
                case "password":
                    // todo 패스워드 암호화 기능 연동 필요
                    String encodedPassword = passwordEncoder.encode((String) value);
                    findUser.setPassword((String) value);
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다 " + key);
            }
        });

        userRepository.save(findUser);
        return new UserUpdateResponseDto(findUser.getUsername(), findUser.getEmail());
    }
}
