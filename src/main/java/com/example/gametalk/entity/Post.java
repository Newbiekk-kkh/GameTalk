package com.example.gametalk.entity;

import com.example.gametalk.entity.base.BaseEntity;
import com.example.gametalk.enums.Genre;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post")
    private List<LikePost> likes = new ArrayList<>();

    public Post() {

    }

    public Post(String title, Genre genre, String content) {
        this.title = title;
        this.genre = genre;
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updatePost(String title, Genre genre, String content) {
        this.title = title;
        this.genre = genre;
        this.content = content;
    }
}
