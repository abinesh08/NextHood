package com.nexthood.user_service.repository;

import com.nexthood.user_service.model.Role;
import com.nexthood.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String name);
    List<User> findByRole(Role role);
}
