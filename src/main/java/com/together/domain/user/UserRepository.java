package com.together.domain.user;

import com.together.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    @Query(value = "SELECT b FROM User b WHERE b.username LIKE %:keyword% OR b.name LIKE %:keyword%"
    )

    List<User> findAllSearch(String keyword);
}
