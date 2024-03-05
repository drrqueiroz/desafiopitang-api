package com.desafiopitang.api.services.Impl;

import com.desafiopitang.api.domain.Car;
import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.CarDTO;
import com.desafiopitang.api.dto.UserDTO;
import com.desafiopitang.api.exception.BusinessException;
import com.desafiopitang.api.mapper.MapStructMapper;
import com.desafiopitang.api.repository.CarRepository;
import com.desafiopitang.api.repository.UserRepository;
import com.desafiopitang.api.security.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class CarServiceImplTest {

    public static final long ID = 1L;
    public static final int YEAR = 2023;
    public static final String LICENSEPLATE = "PCU-8530";
    public static final String MODEL = "Jeep";
    public static  final String COLOR = "Black";

    public static  final User USER = new User();
    public static  final UserDTO USERDTO = new UserDTO();

    public static  final String TOKEN = "Barear 46546546546464654654654454545454";
    public static  final String AUTHORIZATION = "Authorization";





    @Mock
    private CarRepository carRepository;

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private JwtTokenUtil  jwtTokenUtil;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CarServiceImpl carService;
    private Car car;
    private CarDTO carDTO;
    private Optional<Car> optionalCar;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.createCar();
    }

    @Test
    @DisplayName("Carros encontrato com sucesso")
    void findAllSucess() {
        Mockito.when(httpServletRequest.getHeader(Mockito.anyString())).thenReturn(TOKEN);
        Mockito.when(carRepository.findCarByUserList(Mockito.anyLong())).thenReturn(Optional.of(List.of(car)));
        List<Car> carList = carService.findAll();
        assertNotNull(carList);
        assertEquals(Car.class, carList.get(0).getClass());
        assertEquals(ID, carList.get(0).getId());
    }

    @Test
    @DisplayName("Carro encontrado com sucesso")
    void findByIdSucess() {
        Mockito.when(carRepository.findById(Mockito.anyLong())).thenReturn(optionalCar);
        Car car = carService.findById(ID);

        assertNotNull(car);
        assertEquals(Car.class, car.getClass());
        assertEquals(ID, car.getId());
    }

    @Test
    @DisplayName("Carro não encontrado")
    void findByIdNotFound() {
        Mockito.when(carRepository.findById(Mockito.anyLong())).thenThrow(new BusinessException("Carro não encontrado."));
        try {
            Car car = carService.findById(ID);
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
        USER.setId(1L);
        USERDTO.setId(1L);

        car = new Car(ID, YEAR, LICENSEPLATE, MODEL,
                COLOR, USER);

        carDTO = new CarDTO(ID, YEAR, LICENSEPLATE, MODEL,
                COLOR, USERDTO);

        optionalCar = Optional.of(new Car(ID, YEAR, LICENSEPLATE, MODEL,
                COLOR, USER));

        optionalUser = Optional.of(new User(ID, "Davidson", "Queiroz","teste@teste.com",
                LocalDate.parse("1981-01-02"), "drrqueiroz", "123", "5465645464", LocalDateTime.now(), null));
    }
}