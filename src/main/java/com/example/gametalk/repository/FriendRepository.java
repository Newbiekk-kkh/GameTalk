package com.example.gametalk.repository;

import com.example.gametalk.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository <Friend, Long> {
}
