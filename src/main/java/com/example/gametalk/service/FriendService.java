package com.example.gametalk.service;

import com.example.gametalk.dto.FriendRequestDto;
import com.example.gametalk.dto.FriendResponseDto;
import com.example.gametalk.dto.FriendStatusDto;
import com.example.gametalk.entity.Friend;
import com.example.gametalk.entity.User;
import com.example.gametalk.enums.FriendStatus;
import com.example.gametalk.repository.FriendRepository;
import com.example.gametalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.gametalk.enums.FriendStatus.PENDING;


@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public String friendRequest(String email) {
        User sender = userRepository.findUserByIdOrElseThrow(getLoginUserId());
        User receiver = userRepository.findUserByEmailOrElseThrow(email);

        Friend friend = new Friend(PENDING, sender, receiver);

        friendRepository.save(friend);
        return "친구 요청 완료";
    }

    public List<FriendResponseDto> viewFriendList() {
        return friendRepository
                .findAll()
                .stream()
                .map(FriendResponseDto::toDto)
                .toList();
    }

    public List<FriendStatusDto> viewFriendRequestList(Long loginUserId) {
        User loginUser = userRepository.findUserByIdOrElseThrow(loginUserId);
        return friendRepository
                .findByReceiverAndStatus(loginUser, FriendStatus.valueOf("PENDING"))
                .stream()
                .map(FriendStatusDto::toDto)
                .toList();
    }

    private Long getLoginUserId() {
        Long id = 1L;
        return id;
    }
}
