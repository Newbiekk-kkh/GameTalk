package com.example.gametalk.controller;

import com.example.gametalk.dto.FriendRequestDto;
import com.example.gametalk.dto.FriendResponseDto;
import com.example.gametalk.entity.Friend;
import com.example.gametalk.service.FriendService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/friends")
public class FriendController {
    private final FriendService friendService;

    @PostMapping
    public String friendRequest(@PathVariable("userId") Long loginUserId, FriendRequestDto dto) {
        return friendService.friendRequest(dto);
    }
}
