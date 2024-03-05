package com.desafiopitang.api.services;

import com.desafiopitang.api.domain.Car;
import com.desafiopitang.api.dto.CarDTO;

import java.util.List;

public interface CarService {
    /**
     * Consultar todos os carros para o usuário logado
     * @return Lista de carro
     */
    public List<Car> findAll();

    /**
     * Consultar carro pelo id
     * @param id
     * @return Entity carro
     */
    public Car findById(Long id);

    /**
     * Consultar lista de carros cadastradas para o usuário
     * @param id do usuário registrado
     * @return Lista de Carros
     */
    public List<Car> findCarByUserList(Long id);

    /**
     * Salvar carro
     * @param carDTO DTO carro
     * @return Entity carro
     */
    public Car insert(CarDTO carDTO);

    /**
     * Salvar carro
     * @param car Entity carro
     * @return Entity carro
     */
    public Car insert(Car car);

    /**
     * Atualizar as informações do carro
     * @param id Identificador do carrro
     * @param carDTO DTO carro
     * @return Entity carro
     */
    public Car update(Long id, CarDTO carDTO);

    /**
     * Atualizar as informações do carro
     * @param carDTO DTO Carro
     * @return Entity carro
     */
    public Car update(CarDTO carDTO);

    /**
     * Deletar carro
     * @param id carro
     */
    public void delete(Long id);

}
