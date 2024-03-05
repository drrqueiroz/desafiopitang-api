package com.desafiopitang.api.configuration;

import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private UserRepository repositoryUser;

    @Bean
    public boolean loadDB() {
        User user = new User(null, "Davidson", "Queiroz", "queiroz@test.com", LocalDate.parse("1990-05-01", DateTimeFormatter.ISO_LOCAL_DATE), "drrqueiroz", "$2a$10$D6S8QxB5Jq5UnvLzH8TVb.Q59Wb7Sfc/kbJKN6VOySzl348RULAjC", "9123456", LocalDateTime.now(), null);
        LocalDateTime createdAt2 = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.parse("1990-05-01T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME).atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
        User user2 =new User(null, "David", "Lopes", "david@testcom", LocalDate.parse("1990-05-01", DateTimeFormatter.ISO_LOCAL_DATE), "admin", "$2a$10$D6S8QxB5Jq5UnvLzH8TVb.Q59Wb7Sfc/kbJKN6VOySzl348RULAjC", "94556255", createdAt, null);

        repositoryUser.saveAll(List.of(user, user2));
        return true;
    }

}
