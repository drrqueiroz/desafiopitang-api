package com.desafiopitang.api.controllers;

import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.CarDTO;
import com.desafiopitang.api.dto.UserDTO;
import com.desafiopitang.api.mapper.MapStructMapper;
import com.desafiopitang.api.services.UserService;
import org.junit.jupiter.api.Assertions;
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
import springfox.documentation.swagger2.mappers.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserControllerTest {

    public static final long ID = 1L;
    public static final String FIRST_NAME = "Davidson";
    public static final String LAST_NAME = "Queiroz";
    public static final String EMAIL = "teste@teste.com";
    public static final LocalDate BIRTHDAY = LocalDate.parse("1981-01-02");
    public static final String LOGIN = "drrqueiroz";
    public static final String PASSWORD = "123";
    public static final String PHONE = "945666798";
    public static final LocalDate CREATED = LocalDate.now();
    public static final LocalDate LAST_LOGIN = null;
    public static final List<CarDTO> CAR_DTO_LIST = null;

    @InjectMocks
    private UserController userController;

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private UserService userService;


    private User user;
    private UserDTO userDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.createUser();
    }

    @Test
    @DisplayName("Usuarios encontrato com successo.")
    void findAllSuccess() {
        Mockito.when(userService.findAll()).thenReturn(List.of(user));
        Mockito.when(mapStructMapper.toUserDTO(Mockito.any())).thenReturn(userDTO);
        ResponseEntity<List<UserDTO>> response = userController.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());

        assertEquals(UserDTO.class, response.getBody().get(0).getClass());

    }

    @Test
    void findByMe() {
    }

    @Test
    @DisplayName("Usuário encontrato com sucesso")
    void findByIdSuccess() {
        Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(user);
        Mockito.when(mapStructMapper.toUserDTO(Mockito.any())).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(FIRST_NAME, response.getBody().getFirstName());
    }

    @Test
    @DisplayName("Usuário inserido com sucesso.")
    void insertSuccess() {
        Mockito.when(userService.insert(Mockito.any())).thenReturn(user);
        Mockito.when(mapStructMapper.toUserDTO(Mockito.any())).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.insert(userDTO);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Usuário atualizado com sucesso.")
    void updateSuccess() {
        Mockito.when(userService.update(Mockito.any())).thenReturn(user);
        Mockito.when(mapStructMapper.toUserDTO(Mockito.any())).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.update(ID, userDTO);


        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(FIRST_NAME, response.getBody().getFirstName());
    }

    @Test
    @DisplayName("Usuário deletado com success")
    void deleteSuccess() {
        Mockito.doNothing().when(userService).delete(Mockito.anyLong());
        ResponseEntity<Object> response = userController.delete(ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(userService, Mockito.times(1)).delete(Mockito.anyLong());
    }


    private void createUser() {
        user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL,
                BIRTHDAY, LOGIN, PASSWORD, PHONE, CREATED, LAST_LOGIN);

        userDTO = new UserDTO(ID, FIRST_NAME, LAST_NAME, EMAIL,
                BIRTHDAY, LOGIN, PASSWORD, PHONE, CREATED, LAST_LOGIN, CAR_DTO_LIST);
    }
}