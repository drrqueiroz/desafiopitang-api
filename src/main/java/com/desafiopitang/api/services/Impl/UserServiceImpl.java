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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
     * Consultar todos os usuários cadastrados
     * @return List de usuários
     */
    @Override
    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    /**
     * Consultar informacoes do usuário logado
     * @return Objeto DTO
     */
    public UserMeDTO findByMe(String pLogin) {

        Optional<User> user = userRepository.findUserByLogin(pLogin);
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

    public User findByLogin(String pLogin) {

        Optional<User> result = userRepository.findUserByLogin(pLogin);
        return result.orElse(null);
    }

    /**
     * Consular usuário pelo atributo de identificação
     * @param id Atributo Identificador do usuário
     * @return Objeto user entity
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
     * Inserir um objeto usuário
     * @param userDTO Objeto DTO usuário
     * @return Objeto usuário entity
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
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(encryptValue(user.getPassword()));
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
     * Atualizar as informações do usuário
     * @param userDTO Objeto DTO usuário
     * @return Objeto usuário entity
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
        user.setPassword(encryptValue(userDTO.getPassword()));

        return userRepository.save(user);
    }

    /**
     * @param pLogin
     * @return
     */
    public User updateRegistLastLogin(String pLogin) {
        Optional<User> objUser = userRepository.findUserByLogin(pLogin);
        if (objUser.isPresent()){
            objUser.get().setLastLogin(LocalDateTime.now());
            userRepository.save(objUser.get());
            return objUser.get();
        }
        return null;
    }

    /**
     * Deletar o registro do usuário
     * @param id Atributo Identificador do car
     */
    public void delete(long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    /**
     * Encrupt value
     * @param value
     * @return
     */
    private String encryptValue(String value){
        return  new BCryptPasswordEncoder().encode(value);
    }
}

