package com.example.gametalk.controller;

import com.example.gametalk.dto.friends.FriendRequestDto;
import com.example.gametalk.dto.friends.FriendStatusDto;
import com.example.gametalk.dto.friends.UpdateFriendStatusRequestDto;
import com.example.gametalk.exception.authentication.AuthenticationException;
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

    // 친구 요청
    @PostMapping
    public String friendRequest(@RequestBody FriendRequestDto dto) throws AuthenticationException {
        return friendService.friendRequest(dto.getEmail());
    }

    // 친구 목록 보기 , Status = ACCEPTED 인 항목만 불러옴.
    @GetMapping(params = "status=ACCEPTED")
    public ResponseEntity<List<FriendStatusDto>> viewFriendList() throws AuthenticationException {
        List<FriendStatusDto> FriendList = friendService.viewFriendList();
        return new ResponseEntity<> (FriendList, HttpStatus.OK);
    }

    // 친구 요청 목록 보기, Status = PENDING 인 항목만 불러옴.
    @GetMapping(params = "status=PENDING")
    public ResponseEntity<List<FriendStatusDto>> viewFriendRequestList() throws AuthenticationException {
        List<FriendStatusDto> FriendRequestList = friendService.viewFriendRequestList();
        return new ResponseEntity<>(FriendRequestList, HttpStatus.OK);
    }

    // 친구 상태 업데이트 (PENDING 인 친구 요청 상태를 ACCEPTED, DENIED 로 변경)
    @PatchMapping
    public String updateFriendStatus(@RequestBody UpdateFriendStatusRequestDto dto) throws AuthenticationException {

        return friendService.updateFriendStatus(dto.getStatus(), dto.getEmail());
    }
}
