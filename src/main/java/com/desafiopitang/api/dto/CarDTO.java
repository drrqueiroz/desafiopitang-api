package com.desafiopitang.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

    private Long id;

    private int year;

    private String licensePlate;

    private String model;

    private String color;

    private UserDTO user;

}
