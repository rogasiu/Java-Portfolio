package com.example.stm.repository;

import com.example.stm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // SELECT * FROM users WHERE email = ?;
    Optional<User> findByEmail(String email);
}
