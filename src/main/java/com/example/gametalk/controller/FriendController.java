package com.example.gametalk.controller;

import com.example.gametalk.dto.friends.FriendRequestDto;
import com.example.gametalk.dto.friends.FriendStatusDto;
import com.example.gametalk.dto.friends.UpdateFriendStatusRequestDto;
import com.example.gametalk.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {
    private final FriendService friendService;

    @PostMapping
    public String friendRequest(@RequestBody FriendRequestDto dto) {
        return friendService.friendRequest(dto.getEmail());
    }

    @GetMapping(params = "status=ACCEPTED")
    public ResponseEntity<List<FriendStatusDto>> viewFriendList() {
        List<FriendStatusDto> FriendList = friendService.viewFriendList();
        return new ResponseEntity<> (FriendList, HttpStatus.OK);
    }

    @GetMapping(params = "status=PENDING")
    public ResponseEntity<List<FriendStatusDto>> viewFriendRequestList() {
        List<FriendStatusDto> FriendRequestList = friendService.viewFriendRequestList();
        return new ResponseEntity<>(FriendRequestList, HttpStatus.OK);
    }

    @PatchMapping
    public String switchFriendStatus(@RequestBody UpdateFriendStatusRequestDto dto) {

        return friendService.switchFriendStatus(dto.getStatus(), dto.getEmail());
    }
}
