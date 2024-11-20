package com.example.gametalk.controller;

import com.example.gametalk.dto.FriendRequestDto;
import com.example.gametalk.dto.FriendStatusDto;
import com.example.gametalk.dto.UpdateFriendStatusRequestDto;
import com.example.gametalk.service.FriendService;
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
    public ResponseEntity<List<FriendStatusDto>> viewFriendList(@PathVariable("userId") Long loginUserId) {
        List<FriendStatusDto> FriendList = friendService.viewFriendList(loginUserId);
        return new ResponseEntity<> (FriendList, HttpStatus.OK);
    }

    @GetMapping(params = "status=PENDING")
    public ResponseEntity<List<FriendStatusDto>> viewFriendRequestList(@PathVariable("userId") Long loginUserId) {
        List<FriendStatusDto> FriendRequestList = friendService.viewFriendRequestList(loginUserId);
        return new ResponseEntity<>(FriendRequestList, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public String switchFriendStatus(@PathVariable("userId") Long loginUserId, @PathVariable("friendId") Long id, @RequestBody UpdateFriendStatusRequestDto dto) {

        return friendService.switchFriendStatus(loginUserId, dto.getStatus(), dto.getEmail());
    }
}
