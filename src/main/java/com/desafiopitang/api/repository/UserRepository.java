package com.desafiopitang.api.repository;

import com.desafiopitang.api.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);
}
