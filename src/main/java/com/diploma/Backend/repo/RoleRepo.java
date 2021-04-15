package com.diploma.Backend.repo;
import java.util.Optional;


import com.diploma.Backend.model.ERole;
import com.diploma.Backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
