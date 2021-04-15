package com.diploma.Backend.repo;

import com.diploma.Backend.model.Role;
import com.diploma.Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findAllByRolesNotContains(Role role);
    List<User> findAllByRolesContains(Role role);
    List<User> findAllByChairmanId(Long id);

}
