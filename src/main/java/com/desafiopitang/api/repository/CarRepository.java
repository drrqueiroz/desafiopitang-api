package com.desafiopitang.api.repository;

import com.desafiopitang.api.domain.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Optional<Car> findCarByLicensePlate(String licensePlate);

    @Query("from CARS where user.id = :pUserId")
    Optional<List<Car>> findCarByUserList(@Param("pUserId") Long pUserId);

}
