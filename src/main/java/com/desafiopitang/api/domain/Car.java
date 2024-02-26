package com.desafiopitang.api.domain;

import com.desafiopitang.api.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

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

    public void Validator() {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (String.valueOf(this.getYear()).length() < 4) {
            throw new BusinessException("Invalid fields", status);
        }
        else if (this.getLicensePlate() == null || this.getLicensePlate().isBlank()) {
            throw new BusinessException("Missing fields", status);
        }
        else if (this.getModel() == null || this.getModel().isBlank()) {
            throw new BusinessException("Missing fields", status);
        }
        else if (this.getColor() == null || this.getColor().isBlank()) {
            throw new BusinessException("Missing fields", status);
        }
    }

}
