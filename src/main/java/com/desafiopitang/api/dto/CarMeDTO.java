package com.desafiopitang.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarMeDTO {

    private long id;

    private int year;

    private String licensePlate;

    private String model;

    private String color;

}