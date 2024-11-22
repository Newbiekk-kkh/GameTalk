package com.example.gametalk.service;

import com.example.gametalk.dto.friends.FriendStatusDto;
import com.example.gametalk.entity.Friend;
import com.example.gametalk.entity.User;
import com.example.gametalk.enums.FriendStatus;
import com.example.gametalk.exception.authentication.AuthenticationException;
import com.example.gametalk.repository.FriendRepository;
import com.example.gametalk.repository.UserRepository;
import com.example.gametalk.utils.SessionUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.example.gametalk.enums.FriendStatus.PENDING;


@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final SessionUtils sessionUtils;

    @Transactional
    public String friendRequest(String email) throws AuthenticationException {
        User sender = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        User receiver = userRepository.findByEmailOrElseThrow(email);

        if (sender == receiver) {
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }

        if (isAlreadyFriend(sender, receiver)) {
            findFriendStatus(sender, receiver);
            return "친구 요청 실패";
        } else if (isAlreadyFriend(receiver, sender)) {
            findFriendStatus(receiver, sender);
            return "친구 요청 실패";
        } else {
            Friend friend = new Friend(PENDING, sender, receiver);
            friendRepository.save(friend);
            return "친구 요청 완료";
        }
    }


    public List<FriendStatusDto> viewFriendList() throws AuthenticationException {
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

    public List<FriendStatusDto> viewFriendRequestList() throws AuthenticationException{
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        return friendRepository
                .findByReceiverAndStatus(loginUser, FriendStatus.PENDING)
                .stream()
                .map(FriendStatusDto::toDto)
                .toList();
    }

    @Transactional
    public String updateFriendStatus(FriendStatus status, String email) throws AuthenticationException {
        User loginUser = userRepository.findByEmailOrElseThrow(sessionUtils.getLoginUserEmail());
        User sender = userRepository.findByEmailOrElseThrow(email);
        Friend pendingFriendRequest = friendRepository.findBySenderAndReceiverAndStatus(sender, loginUser, FriendStatus.valueOf("PENDING"));

        pendingFriendRequest.updateFriend(status);

        return switch (status) {
            case PENDING -> "친구 요청이 보류 되었습니다.";
            case DENIED -> "친구요청이 거절되었습니다.";
            case ACCEPTED -> "친구요청이 승인되었습니다.";
        };
    }

    public boolean isAlreadyFriend(User user1, User user2) {
        return friendRepository.findByReceiverAndSender(user1, user2) != null;
    }

    public void findFriendStatus(User user1, User user2) {
        Friend friend = friendRepository.findByReceiverAndSender(user1, user2);

        switch (friend.getStatus()) {
            case PENDING -> throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "이미 보낸 친구 요청입니다.");
            case DENIED -> throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "이미 거절 당한 친구요청입니다.");
            case ACCEPTED -> throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "이미 친구 상태입니다.");
        };
    }
}
