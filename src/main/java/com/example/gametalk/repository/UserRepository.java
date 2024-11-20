package com.example.gametalk.repository;

import com.example.gametalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
   Optional<User> findUserByUsername(String username);

    default User findUserByUsernameOrElseThrow(String username) {
        return findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    // UserId 존재 유무 확인
    default User findByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다"));
    }

    // Email 존재 유무 확인
    Optional<User> findByEmail(String email);

    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."));
    }
}
