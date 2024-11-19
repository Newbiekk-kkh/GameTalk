package com.example.gametalk.repository;

import com.example.gametalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * @apiNote 저장된 유저 데이터 중 같은 email 이 있는지 체크
     * @param email
     * @return 중복되면 true 중복되지 않으면 false 반환
     */
    boolean existsByEmail(String email);
}
