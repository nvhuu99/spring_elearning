package com.demo.repositories;

import com.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsernameAndAccessToken(String username, String token);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
