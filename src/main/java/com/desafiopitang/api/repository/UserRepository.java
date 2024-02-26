package com.desafiopitang.api.repository;

import com.desafiopitang.api.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);
}
