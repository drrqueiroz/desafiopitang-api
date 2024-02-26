package com.desafiopitang.api.services.Impl;


import com.desafiopitang.api.domain.Car;
import com.desafiopitang.api.dto.CarDTO;
import com.desafiopitang.api.exception.BusinessException;
import com.desafiopitang.api.mapper.MapStructMapper;
import com.desafiopitang.api.repository.CarRepository;
import com.desafiopitang.api.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private MapStructMapper mapStructMapper;


    public List<Car> findAll(){
        return this.carRepository.findAll();
    }

    public Car findById(Long id) {

        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            return car.get();
        }else {
            throw new BusinessException("Carro não encontrato.", HttpStatus.NOT_FOUND);
        }

    }

    /**
     *
     * @param id
     * @return
     */
    public List<Car> findCarByUserList(Long id) {

        Optional<List<Car>>  cars = carRepository.findCarByUserList(id);
        if (cars.isPresent()) {
            return cars.get();
        }else {
            throw new BusinessException("Carro não encontrato.", HttpStatus.NOT_FOUND);
        }

    }

    /**
     *
     * @param carDTO
     * @return
     */
    public Car insert(CarDTO carDTO) {

        Car car = mapStructMapper.toCarEntity(carDTO);
        return insert(car);
    }

    /**
     *
     * @param car
     * @return
     */
    public Car insert(Car car) {

        Optional<Car> result = carRepository.findCarByLicensePlate(car.getLicensePlate());
        if (result.isPresent()) {
            throw new BusinessException("License plate already exists.", HttpStatus.BAD_REQUEST);
        }
        car.Validator();
        return carRepository.save(car);
    }

    /**
     *
     * @param id
     * @param carDTO
     * @return
     */
    public Car update(Long id, CarDTO carDTO) {

        findById(id);

        Optional<Car> result = carRepository.findCarByLicensePlate(carDTO.getLicensePlate());
        if (result.isPresent() && !result.get().getId().equals(id)) {
            throw new BusinessException("License plate already exists.", HttpStatus.BAD_REQUEST);
        }

        Car newcar = mapStructMapper.toCarEntity(carDTO);
        newcar.setId(id);
        return carRepository.save(newcar);
    }

    /**
     *
     * @param id
     */
    public void delete(Long id) {
        findById(id);
        carRepository.deleteById(id);
    }
}

