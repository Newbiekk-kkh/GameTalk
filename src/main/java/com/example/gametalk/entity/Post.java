package com.example.gametalk.entity;

import com.example.gametalk.enums.FriendStatus;
import com.example.gametalk.enums.Genre;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post() {

    }

    public Post(Genre genre, String title, String content) {
        this.genre = genre;
        this.title = title;
        this.content = content;
    }
}
