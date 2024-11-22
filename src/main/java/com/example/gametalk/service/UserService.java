package com.example.gametalk.service;

import com.example.gametalk.entity.User;
import com.example.gametalk.repository.UserRepository;
import com.example.gametalk.utils.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.gametalk.config.PasswordEncoder;
import com.example.gametalk.dto.users.UserResponseDto;
import com.example.gametalk.exception.authentication.AuthenticationErrorCode;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.exception.validation.ValidationErrorCode;
import com.example.gametalk.exception.validation.ValidationException;
import jakarta.transaction.Transactional;
import lombok.Getter;

import java.util.Optional;


@Getter
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionUtils sessionUtils;


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

    // 프로필 조회 기능
    public UserResponseDto findUserById(Long userId) throws AuthenticationException {
        User user = userRepository.findByIdOrElseThrow(userId);
        return User.toDto(user);
    }

    // 프로필 수정 기능
    @Transactional
    public UserResponseDto updateUser(Long userId, String name, String email, String password, String newPassword) throws AuthenticationException, ValidationException {
        //권환 확인
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.checkAuthorization(findUserById(userId).getEmail()));

        // 비빌번호 확인
        checkPassword(password, loginUser);

        // 새로운 비밀번호가 이전 비밀번호랑 동일하지 확인
        if (password.equals(newPassword)) {
            throw new ValidationException(ValidationErrorCode.PASSWORD_NOT_ALLOWED);
        }

        // 세션 새로고침
        if (!loginUser.getEmail().equals(email)) {
            loginUser.setEmail(email);
            sessionUtils.reloadSession(email);
        }

        // 새로운 비밀번호 저장
        String encodedPassword = passwordEncoder.encode(newPassword);
        loginUser.updateUserInfo(name, email, encodedPassword);

        userRepository.save(loginUser);

        return new UserResponseDto(loginUser.getEmail(), loginUser.getUsername());
    }

    // 비밀번호 체크
    private void checkPassword(String password, User findUser) throws AuthenticationException {
        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new AuthenticationException(AuthenticationErrorCode.PASSWORD_INCORRECT);
        }
    }
}
