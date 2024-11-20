package com.example.gametalk.service;

import com.example.gametalk.dto.FriendRequestDto;
import com.example.gametalk.dto.FriendResponseDto;
import com.example.gametalk.entity.Friend;
import com.example.gametalk.entity.User;
import com.example.gametalk.repository.FriendRepository;
import com.example.gametalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.gametalk.enums.FriendStatus.PENDING;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public String friendRequest(FriendRequestDto dto) {
        User sender = userRepository.findUserByIdOrElseThrow(getLoginUserId());
        User receiver = userRepository.findUserByEmailOrElseThrow(dto.getEmail());

        Friend friend = new Friend(PENDING, sender, receiver);

        friendRepository.save(friend);
        return "친구 요청 완료";
    }

    private Long getLoginUserId() {
        Long id = 1L;
        return id;
    }
}
