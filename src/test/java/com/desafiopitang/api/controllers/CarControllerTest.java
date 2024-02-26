package com.desafiopitang.api.controllers;

import com.desafiopitang.api.domain.Car;
import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.CarDTO;
import com.desafiopitang.api.dto.UserDTO;
import com.desafiopitang.api.mapper.MapStructMapper;
import com.desafiopitang.api.services.CarService;
import com.desafiopitang.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CarControllerTest {

    public static final long ID = 1L;
    public static final int YEAR = 2023;
    public static final String LICENSEPLATE = "PCU-8530";
    public static final String MODEL = "Jeep";
    public static  final String COLOR = "Black";

    @InjectMocks
    private CarController carController;

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private CarService carService;
    private Car car;
    private CarDTO carDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.createCar();
    }

    @Test
    @DisplayName("Carros encontrato com successo.")
    void findAllSuccess() {
        Mockito.when(carService.findAll()).thenReturn(List.of(car));
        Mockito.when(mapStructMapper.toCarDTO(Mockito.any())).thenReturn(carDTO);
        ResponseEntity<List<CarDTO>> response = carController.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());

        assertEquals(CarDTO.class, response.getBody().get(0).getClass());

    }

    @Test
    @DisplayName("Carro encontrato com sucesso")
    void findByIdSuccess() {
        Mockito.when(carService.findById(Mockito.anyLong())).thenReturn(car);
        Mockito.when(mapStructMapper.toCarDTO(Mockito.any())).thenReturn(carDTO);
        ResponseEntity<CarDTO> response = carController.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(CarDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(LICENSEPLATE, response.getBody().getLicensePlate());
    }

    @Test
    @DisplayName("Carro inserido com sucesso.")
    void insertSuccess() {
        Mockito.when(carService.insert((CarDTO) Mockito.any())).thenReturn(car);
        Mockito.when(mapStructMapper.toCarDTO(Mockito.any())).thenReturn(carDTO);
        ResponseEntity<CarDTO> response = carController.insert(carDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(CarDTO.class, response.getBody().getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Usuário atualizado com sucesso.")
    void updateSuccess() {
        Mockito.when(carService.update(Mockito.any())).thenReturn(car);
        Mockito.when(mapStructMapper.toCarDTO(Mockito.any())).thenReturn(carDTO);
        ResponseEntity<CarDTO> response = carController.update(ID, carDTO);


        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(CarDTO.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(LICENSEPLATE, response.getBody().getLicensePlate());
    }

    @Test
    @DisplayName("Usuário deletado com success")
    void deleteSuccess() {
        Mockito.doNothing().when(carService).delete(Mockito.anyLong());
        ResponseEntity<Object> response = carController.delete(ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(carService, Mockito.times(1)).delete(Mockito.anyLong());
    }

    private void createCar() {
        User user = new User();
        user.setId(1L);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        car = new Car(ID, YEAR, LICENSEPLATE, MODEL,
                COLOR, user);

        carDTO = new CarDTO(ID, YEAR, LICENSEPLATE, MODEL,
                COLOR, userDTO);
    }

}