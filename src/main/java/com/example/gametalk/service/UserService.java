package com.example.gametalk.service;

import com.example.gametalk.entity.User;
import com.example.gametalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.gametalk.config.PasswordEncoder;
import com.example.gametalk.dto.UserResponseDto;
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
    public UserResponseDto findUserById(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);
        return User.toDto(user);
    }

    // 프로필 수정 기능
    public UserResponseDto updateUser(Long userId, Map<String, Object> updates) {
        User findUser = userRepository.findByIdOrElseThrow(userId);

        updates.forEach((key, value) -> {
            switch (key) {
                case "username":
                    findUser.setUsername((String) value);
                    break;
                case "email":
                    String email = (String) value;
                    if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바르지 않은 이메일 형식입니다.");
                    }
                    findUser.setEmail(email);
                    break;
                case "password":
                    String password = (String) value;
                    if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "비밀번호는 대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 이상 포함해야 하며, 8~20자 사이여야 합니다.");
                    }
                    // 비밀번호 암호화
                    String encodedPassword = passwordEncoder.encode(password);
                    findUser.setPassword(encodedPassword);
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다 " + key);
            }
        });

        userRepository.save(findUser);
        return new UserResponseDto(findUser.getUsername(), findUser.getEmail());
    }
}
