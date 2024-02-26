package com.desafiopitang.api.services.Impl;


import com.desafiopitang.api.domain.Car;
import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.CarDTO;
import com.desafiopitang.api.dto.UserDTO;
import com.desafiopitang.api.dto.UserMeDTO;
import com.desafiopitang.api.exception.BusinessException;
import com.desafiopitang.api.mapper.MapStructMapper;
import com.desafiopitang.api.repository.UserRepository;
import com.desafiopitang.api.services.CarService;
import com.desafiopitang.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private MapStructMapper mapStructMapper;


    /**
     *
     * @return
     */
    @Override
    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    public UserMeDTO findByMe() {

        Optional<User> user = userRepository.findUserByLogin("hello.worlde");
        if (user.isPresent()) {
            UserMeDTO userMeDto = mapStructMapper.toUserMeDTO(user.get());
            List<Car> cars = carService.findCarByUserList(user.get().getId());
            if (cars == null) {
                throw new BusinessException("Carro(s) não encontrato(s).", HttpStatus.NOT_FOUND);
            }
            List<CarDTO> listCarDTO = new ArrayList<>();
            for (Car car : cars) {
                CarDTO carDTO = mapStructMapper.toCarDTO(car);
                listCarDTO.add(carDTO);
            }
            userMeDto = userMeDto.setCars(listCarDTO);
            return userMeDto;
        }else {
            throw new BusinessException("User não encontrato.", HttpStatus.NOT_FOUND);
        }

    }

    /**
     *
     * @param id
     * @return
     */
    public User findById(long id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }else {
            throw new BusinessException("User não encontrato.", HttpStatus.NOT_FOUND);
        }

    }
    /**
     *
     * @param userDTO
     * @return
     */
    public User insert(UserDTO userDTO) {

        Optional<User> result = userRepository.findUserByLogin(userDTO.getLogin());
        if (result.isPresent()) {
            throw new BusinessException("Login already exists.", HttpStatus.BAD_REQUEST);
        }

        result = userRepository.findUserByEmail(userDTO.getEmail());
        if (result.isPresent()) {
            throw new BusinessException("Email already exists.", HttpStatus.BAD_REQUEST);
        }

        User user = mapStructMapper.toUserEntity(userDTO);
        user.setCreatedAt(LocalDate.now());
        user.Validator();
        User newUser = userRepository.save(user);

        if (userDTO.getCars() != null) {

            for (CarDTO carDTO : userDTO.getCars()) {
                carDTO.setUser(userDTO);
                Car car = mapStructMapper.toCarEntity(carDTO);
                car.setUser(newUser);
                carService.insert(car);
            }
        }

        return newUser;


    }

    /**
     *
     * @param userDTO
     * @return
     */
    public User update(UserDTO userDTO) {

        User user = findById(userDTO.getId());

        if (!user.getEmail().equals(userDTO.getEmail())) {
            Optional<User> result  = userRepository.findUserByEmail(userDTO.getEmail());
            if (result.isPresent() && !result.get().getId().equals(userDTO.getId())) {
                throw new BusinessException("Email already exists.", HttpStatus.BAD_REQUEST);
            }
        }

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setBirthday(userDTO.getBirthday());
        user.setPhone(userDTO.getPhone());

        return userRepository.save(user);
    }

    /**
     *
     * @param id
     */
    public void delete(long id) {
        findById(id);
        userRepository.deleteById(id);
    }
}

