package com.testproject.urltrimmer.repository;

import com.testproject.urltrimmer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Integer> {

    Optional<User> getByEmail(String email);


}
