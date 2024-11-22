package com.example.gametalk.dto.friends;

import com.example.gametalk.entity.Friend;
import lombok.Getter;

@Getter
public class FriendStatusDto {
    private String senderName;
    private String senderEmail;
    private String receiverName;
    private String receiverEmail;

    public FriendStatusDto() {
    }

    public FriendStatusDto(String senderName, String senderEmail, String receiverName, String receiverEmail) {
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.receiverName = receiverName;
        this.receiverEmail = receiverEmail;
    }

    public static FriendStatusDto toDto(Friend friend) {
        return new FriendStatusDto(
                friend.getSender().getUsername(),
                friend.getSender().getEmail(),
                friend.getReceiver().getUsername(),
                friend.getReceiver().getEmail()
        );
    }


//    public static FriendStatusDto receiverToDto(Friend friend) {
//        return new FriendStatusDto(
//                friend.getSender().getUsername(),
//                friend.getSender().getEmail()
//        );
//    }
//
//    public static FriendStatusDto senderToDto(Friend friend) {
//        return new FriendStatusDto(
//                friend.getReceiver().getUsername(),
//                friend.getReceiver().getEmail()
//        );
//    }

}
