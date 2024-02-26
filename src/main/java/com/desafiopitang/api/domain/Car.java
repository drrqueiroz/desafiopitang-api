package com.desafiopitang.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "CARS")
@Table(name = "CARS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car  {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonProperty("year")
    @Column(name = "ANO", nullable = false)
    private int year;

    @Column(name = "LICENSEPLATE", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "MODEL", nullable = false)
    private String model;

    @Column(name = "COLOR", nullable = false)
    private String color;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


}
