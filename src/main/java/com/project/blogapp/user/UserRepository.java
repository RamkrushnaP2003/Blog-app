package com.project.blogapp.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByUsername(String username);
}
