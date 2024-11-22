package com.example.gametalk.repository;

import com.example.gametalk.entity.Friend;
import com.example.gametalk.entity.User;
import com.example.gametalk.enums.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository <Friend, Long> {
    // 받는 사람과 상태로 친구목록 찾기
    List<Friend> findByReceiverAndStatus (User receiver, FriendStatus status);

    // 보낸 사람과 상태로 친구목록 찾기
    List<Friend> findBySenderAndStatus (User sender, FriendStatus status);

    // 보낸 사람과 받는사람 그리고 상태로 친구요청 찾기
    Optional<Friend> findBySenderAndReceiverAndStatus (User sender, User receiver, FriendStatus status);

    // 받는 사람과 보낸사람으로 친구요청 찾기
    Friend findByReceiverAndSender(User receiver, User sender);

    default Friend findBySenderAndReceiverAndStatusOrElseThrow (User sender, User receiver, FriendStatus status) {
        return findBySenderAndReceiverAndStatus(sender, receiver, status)
                .orElseThrow(()
                        -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "보류중인 친구 요청이 없습니다.")
                );
    }
}
