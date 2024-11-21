package com.example.gametalk.repository;

import com.example.gametalk.entity.User;
import com.example.gametalk.exception.authentication.AuthenticationErrorCode;
import com.example.gametalk.exception.authentication.AuthenticationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findUserByUsername(String username);

    default User findUserByUsernameOrElseThrow(String username) throws AuthenticationException {
        return findUserByUsername(username).orElseThrow(() -> new AuthenticationException(AuthenticationErrorCode.USER_NOT_FOUND));
    }

    // UserId 존재 유무 확인
    default User findByIdOrElseThrow(long id) throws AuthenticationException {
        return findById(id).orElseThrow(() -> new AuthenticationException(AuthenticationErrorCode.USER_NOT_FOUND));
    }

    // Email 존재 유무 확인
    Optional<User> findByEmail(String email);

    default User findByEmailOrElseThrow(String email) throws AuthenticationException {
        return findByEmail(email).orElseThrow(() -> new AuthenticationException(AuthenticationErrorCode.EMAIL_NOT_FOUND));
    }
}
