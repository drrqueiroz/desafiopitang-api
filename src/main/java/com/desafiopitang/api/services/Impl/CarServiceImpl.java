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

    /**
     * Consular um carro pelo atributo identificador
     * @param id Atributo Identificador do car
     * @return Objeto car entity
     */
    public Car findById(Long id) {

        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            return car.get();
        }else {
            throw new BusinessException("Carro não encontrato.", HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Consultar uma lista de carro do atributo identificador do usuário
     * @param id  Atributo Identificador do car
     * @return List car
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
     * Inserir um objeto car
     * @param carDTO Objeto DTO car
     * @return Ojbeto car entity
     */
    public Car insert(CarDTO carDTO) {

        Car car = mapStructMapper.toCarEntity(carDTO);
        return insert(car);
    }

    /**
     * Inserir um objeto car
     * @param car Objeto car entity
     * @return Objeto entity car
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
     * Atualizar as informações do car
     * @param id Atributo Identificador do car
     * @param carDTO Objeto car DTO
     * @return Objeto car entity
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
     * Deletar o registro  car
     * @param id Atributo Identificador do car
     */
    public void delete(Long id) {
        findById(id);
        carRepository.deleteById(id);
    }
}

