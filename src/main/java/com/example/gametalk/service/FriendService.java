package com.example.gametalk.service;

import com.example.gametalk.dto.friends.FriendStatusDto;
import com.example.gametalk.dto.users.UserProfileResponseDto;
import com.example.gametalk.dto.users.UserResponseDto;
import com.example.gametalk.entity.Friend;
import com.example.gametalk.entity.User;
import com.example.gametalk.enums.FriendStatus;
import com.example.gametalk.repository.FriendRepository;
import com.example.gametalk.repository.UserRepository;
import com.example.gametalk.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.example.gametalk.enums.FriendStatus.DENIED;
import static com.example.gametalk.enums.FriendStatus.PENDING;


@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final SessionUtils sessionUtils;

    @Transactional
    public String friendRequest(String email) {
        User sender = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        User receiver = userRepository.findByEmailOrElseThrow(email);

        Friend friend = new Friend(PENDING, sender, receiver);

        friendRepository.save(friend);
        return "친구 요청 완료";
    }

    public List<FriendStatusDto> viewFriendList() {
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());

        List<Friend> friendsAsSender = friendRepository.findBySenderAndStatus(loginUser, FriendStatus.valueOf("ACCEPTED"));
        List<Friend> friendsAsReceiver = friendRepository.findByReceiverAndStatus(loginUser, FriendStatus.valueOf("ACCEPTED"));

        List<Friend> acceptedFriendsList = new ArrayList<>();
        acceptedFriendsList.addAll(friendsAsSender);
        acceptedFriendsList.addAll(friendsAsReceiver);

        return acceptedFriendsList
                .stream()
                .map(FriendStatusDto::toDto)
                .toList();
    }

    public List<FriendStatusDto> viewFriendRequestList() {
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        System.out.println("Current user email: " + sessionUtils.getLoginUserEmail());

        return friendRepository
                .findByReceiverAndStatus(loginUser, FriendStatus.PENDING)
                .stream()
                .map(FriendStatusDto::toDto)
                .toList();

    }

    @Transactional
    public String switchFriendStatus(FriendStatus status, String email) {
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        User sender = userRepository.findByEmailOrElseThrow(email);
        Friend pendingFriendRequest = friendRepository.findBySenderAndReceiverAndStatus(sender, loginUser, FriendStatus.valueOf("PENDING"));

        pendingFriendRequest.updateFriendStatus(status);

        if (status == DENIED) {
            return "친구요청이 거절되었습니다.";
        } else {
            return "친구요청이 승낙되었습니다.";
        }
    }
}
