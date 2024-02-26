package com.desafiopitang.api.services;

import com.desafiopitang.api.domain.Car;
import com.desafiopitang.api.dto.CarDTO;

import java.util.List;

public interface CarService {
    /**
     *
     * @return
     */
    public List<Car> findAll();

    /**
     *
     * @param id
     * @return
     */
    public Car findById(Long id);

    /**
     *
     * @param id
     * @return
     */
    public List<Car> findCarByUserList(Long id);

    /**
     *
     * @param carDTO
     * @return
     */
    public Car insert(CarDTO carDTO);

    /**
     *
     * @param car
     * @return
     */
    public Car insert(Car car);

    /**
     *
     * @param id
     * @param carDTO
     * @return
     */
    public Car update(Long id, CarDTO carDTO);

    /**
     *
     * @param carDTO
     * @return
     */
    public Car update(CarDTO carDTO);

    /**
     *
     * @param id
     * @return
     */
    public void delete(Long id);

}
