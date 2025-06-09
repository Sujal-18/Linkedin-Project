package com.sujal.LinkedInProject.userService.repositories;

import com.sujal.LinkedInProject.userService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
