package com.example.gametalk.entity;

import com.example.gametalk.enums.FriendStatus;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendStatus status;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    public Friend() {

    }

    public Friend(FriendStatus status, User sender, User receiver) {
        this.status = status;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void updateFriend(FriendStatus status) {
        this.status = status;
    }
}
