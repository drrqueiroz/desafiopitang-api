package com.desafiopitang.api.repository;

import com.desafiopitang.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from USERS where login = :pLogin")
    UserDetails findUserByLoginAuth(@Param("pLogin") String pLogin);
    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);

}
