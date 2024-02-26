package com.desafiopitang.api.controllers;


import com.desafiopitang.api.domain.Car;
import com.desafiopitang.api.dto.CarDTO;
import com.desafiopitang.api.mapper.MapStructMapper;
import com.desafiopitang.api.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/cars")
public class CarController {

    @Autowired
    private MapStructMapper mapStructMapper;

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDTO>> findAll() {

        List<CarDTO> list = carService.findAll().stream().map(x -> mapStructMapper.toCarDTO(x)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<CarDTO> findById(@PathVariable Long id) {

        Car car = carService.findById(id);
        return new ResponseEntity<CarDTO>(mapStructMapper.toCarDTO(car), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<CarDTO> insert(@RequestBody CarDTO carDTO){

        Car car = carService.insert(carDTO);
        return new ResponseEntity<>(mapStructMapper.toCarDTO(car), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CarDTO> update(@PathVariable long id, @RequestBody CarDTO carDTO) {

        Car car = carService.update(id, carDTO);
        return new ResponseEntity<>(mapStructMapper.toCarDTO(car), HttpStatus.OK);

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id){
        carService.delete(id);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "Registro deletado com sucesso.");
        map.put("status", HttpStatus.OK.value());
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }



}

