package com.testproject.urltrimmer.repository;

import com.testproject.urltrimmer.model.User;

import java.util.Optional;

public interface JpaUserRepository extends BaseRepository<User> {

    Optional<User> getByEmail(String email);


}
