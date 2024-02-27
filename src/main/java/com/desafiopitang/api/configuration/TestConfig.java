package com.desafiopitang.api.configuration;

import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private UserRepository repositoryUser;

    @Bean
    public boolean loadDB() {
        User user = new User(null, "Davidson", "Queiroz", "queiroz@test.com", LocalDate.parse("1990-05-01", DateTimeFormatter.ISO_LOCAL_DATE), "drrqueiroz", "123", "9123456", LocalDate.now(), null);
        User user2 =new User(null, "David", "Lopes", "david@testcom", LocalDate.parse("1990-05-01", DateTimeFormatter.ISO_LOCAL_DATE), "admin", "admin", "94556255", LocalDate.now(), null);

        repositoryUser.saveAll(List.of(user, user2));
        return true;
    }

}
