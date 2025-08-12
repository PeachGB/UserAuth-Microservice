package com.peachgb.userService.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.peachgb.userService.users.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
