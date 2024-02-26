package com.desafiopitang.api.repository;

import com.desafiopitang.api.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findCarByLicensePlate(String licensePlate);

    @Query("from CARS where user.id = :pUserId")
    Optional<List<Car>> findCarByUserList(@Param("pUserId") Long pUserId);

}
