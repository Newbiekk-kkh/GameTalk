package com.example.gametalk.controller;

import com.example.gametalk.dto.FriendRequestDto;
import com.example.gametalk.dto.FriendResponseDto;
import com.example.gametalk.entity.Friend;
import com.example.gametalk.service.FriendService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/friends")
public class FriendController {
    private final FriendService friendService;

    @PostMapping
    public String friendRequest(@PathVariable("userId") Long loginUserId, @RequestBody FriendRequestDto dto) {
        return friendService.friendRequest(dto.getEmail());
    }

    @GetMapping(params = "status=ACCEPTED")
    public ResponseEntity<List<FriendResponseDto>> viewFriendList(@PathVariable("userId") Long loginUserId, FriendResponseDto dto) {
        List<FriendResponseDto> friendResponseDtoList = friendService.viewFriendList();
        return new ResponseEntity<> (friendResponseDtoList, HttpStatus.OK);
    }
}
