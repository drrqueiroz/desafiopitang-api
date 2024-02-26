package com.desafiopitang.api.services.Impl;

import com.desafiopitang.api.domain.Car;
import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.CarDTO;
import com.desafiopitang.api.dto.UserDTO;
import com.desafiopitang.api.exception.BusinessException;
import com.desafiopitang.api.mapper.MapStructMapper;
import com.desafiopitang.api.repository.CarRepository;
import com.desafiopitang.api.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class CarServiceImplTest {

    public static final long ID = 1L;
    public static final int YEAR = 2023;
    public static final String LICENSEPLATE = "PCU-8530";
    public static final String MODEL = "Jeep";
    public static  final String COLOR = "Black";



    @Mock
    private CarRepository carRepository;

    @Mock
    private MapStructMapper mapStructMapper;

    @InjectMocks
    private CarServiceImpl carService;
    private Car car;
    private CarDTO carDTO;
    private Optional<Car> optionalCar;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.createCar();
    }

    @Test
    @DisplayName("Carros encontrato com sucesso")
    void findAllSucess() {
        Mockito.when(carRepository.findAll()).thenReturn(List.of(car));
        List<Car> carList = carService.findAll();
        assertNotNull(carList);
        assertEquals(Car.class, carList.get(0).getClass());
        assertEquals(ID, carList.get(0).getId());
    }



    @Test
    @DisplayName("Carro encontrado com sucesso")
    void findByIdSucess() {
        Mockito.when(carRepository.findById(Mockito.anyLong())).thenReturn(optionalCar);
        Car user = carService.findById(ID);

        assertNotNull(user);
        assertEquals(Car.class, user.getClass());
        assertEquals(ID, user.getId());
    }

    @Test
    @DisplayName("Carro não encontrado")
    void findByIdNotFound() {
        Mockito.when(carRepository.findById(Mockito.anyLong())).thenThrow(new BusinessException("Carro não encontrado."));
        try {
            Car user = carService.findById(ID);
        }catch (Exception e){
            assertEquals(BusinessException.class, e.getClass());
            assertEquals("Carro não encontrado.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Create car success.")
    void insertSuccess() {
        Mockito.when(mapStructMapper.toCarEntity(any())).thenReturn(car);
        Mockito.when(carRepository.save(any())).thenReturn(car);
        Car car = carService.insert(carDTO);
        assertNotNull(car);
        assertEquals(Car.class, car.getClass());
        assertEquals(ID, car.getId());
        assertEquals(LICENSEPLATE, car.getLicensePlate());
    }

    @Test
    @DisplayName("Create car sem success license plate ja existe.")
    void insertNoSuccessLicensePlateExist() {
        Mockito.when(carRepository.findCarByLicensePlate(anyString())).thenReturn(optionalCar);
        Mockito.when(mapStructMapper.toCarEntity(any())).thenReturn(car);
        try {
            Car car = carService.insert(carDTO);
        }catch (Exception e){
            assertEquals(BusinessException.class, e.getClass());
            assertEquals("License plate already exists.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Carro atualizado com success.")
    void updateSuccess() {
        Mockito.when(carRepository.findById(Mockito.anyLong())).thenReturn(optionalCar);
        Mockito.when(carRepository.findCarByLicensePlate(anyString())).thenReturn(optionalCar);
        Mockito.when(mapStructMapper.toCarEntity(any())).thenReturn(car);
        Mockito.when(carRepository.save(any())).thenReturn(car);
        Car car = carService.update(carDTO);
        assertNotNull(car);
        assertEquals(Car.class, car.getClass());
        assertEquals(ID, car.getId());
        assertEquals(LICENSEPLATE, car.getLicensePlate());
    }

    @Test
    @DisplayName("Car não atualizado email ja existe.")
    void updateNoSuccessLicensePlateExist() {
        Mockito.when(carRepository.findById(Mockito.anyLong())).thenReturn(optionalCar);
        Mockito.when(carRepository.findCarByLicensePlate(anyString())).thenReturn(optionalCar);
        Mockito.when(mapStructMapper.toCarEntity(any())).thenReturn(car);
        try {
            optionalCar.get().setId(2L);
           // optionalCar.get().setEmail("all@all.com");
            Car user = carService.update(carDTO);

        }catch (Exception e){
            assertEquals(BusinessException.class, e.getClass());
            assertEquals("License plate already exists.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Car deletado com successo.")
    void deleteSuccess() {
        Mockito.when(carRepository.findById(Mockito.anyLong())).thenReturn(optionalCar);
        Mockito.doNothing().when(carRepository).deleteById(Mockito.anyLong());
        carRepository.deleteById(ID);
        Mockito.verify(carRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    @DisplayName("Car não pode ser deletado")
    void deleteNotSuccess() {
        try{
            Mockito.when(carRepository.findById(Mockito.anyLong())).thenReturn(null);
            carRepository.deleteById(ID);

        }catch (Exception e){
            assertEquals(BusinessException.class, e.getClass());
            assertEquals("Car não encontrato.", e.getMessage());
        }

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

        optionalCar = Optional.of(new Car(ID, YEAR, LICENSEPLATE, MODEL,
                COLOR, user));
    }
}