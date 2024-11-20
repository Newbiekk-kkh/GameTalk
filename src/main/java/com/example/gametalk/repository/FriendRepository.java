package com.example.gametalk.repository;

import com.example.gametalk.entity.Friend;
import com.example.gametalk.entity.User;
import com.example.gametalk.enums.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository <Friend, Long> {
    List<Friend> findByStatus(FriendStatus status);
    List<Friend> findByReceiverAndStatus(User receiver, FriendStatus status);
    List<Friend> findBySenderAndStatus(User sender, FriendStatus status);

    default Friend findByLoginUserIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Does not exist id = " + id)
                );
    }
}
