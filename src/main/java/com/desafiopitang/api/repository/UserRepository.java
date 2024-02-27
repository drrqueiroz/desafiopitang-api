package com.desafiopitang.api.repository;

import com.desafiopitang.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findUserByLogin2(String login);
    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);

}
