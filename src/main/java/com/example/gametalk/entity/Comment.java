package com.example.gametalk.entity;

import com.example.gametalk.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> likes = new ArrayList<>();

    @Column(nullable = false)
    private String comment;

    public Comment() {

    }

    public Comment(User findUser, Post post, @NotBlank String comment) {
        this.user = findUser;
        this.post = post;
        this.comment = comment;
    }

    public void setComment(@NotBlank String comment) {
        this.comment = comment;
    }
}
