package com.desafiopitang.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record UserMeDTO(String firstName, String lastName, String email, LocalDate birthday, String login, String phone, List<CarDTO> cars, LocalDateTime createdAt, LocalDateTime lastLogin ) {

    public UserMeDTO setCars(List<CarDTO> cars) {
        return new UserMeDTO(firstName(), lastName(), email(), birthday(), login(), phone(), cars, createdAt(), lastLogin());
    }

}

