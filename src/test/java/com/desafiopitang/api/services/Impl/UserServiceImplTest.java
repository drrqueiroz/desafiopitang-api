package com.desafiopitang.api.services.Impl;

import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.CarDTO;
import com.desafiopitang.api.dto.UserDTO;
import com.desafiopitang.api.exception.BusinessException;
import com.desafiopitang.api.mapper.MapStructMapper;
import com.desafiopitang.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class UserServiceImplTest {

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
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MapStructMapper mapStructMapper;


    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.createUser();
    }

    @Test
    @DisplayName("Usuários encontrato com sucesso")
    void findAllSucess() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> userList = userService.findAll();
        assertNotNull(userList);
        assertEquals(User.class, userList.get(0).getClass());
        assertEquals(ID, userList.get(0).getId());
    }

    @Test
    void findByMe() {
    }

    @Test
    @DisplayName("Usuário encontrato com sucesso")
    void findByIdSucess() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);
        User user = userService.findById(ID);

        assertNotNull(user);
        assertEquals(User.class, user.getClass());
        assertEquals(ID, user.getId());
    }

    @Test
    @DisplayName("Usuário não encontrato")
    void findByIdNotFound() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new BusinessException("Usuário não encontrado."));
        try {
            User user = userService.findById(ID);
        }catch (Exception e){
            assertEquals(BusinessException.class, e.getClass());
            assertEquals("Usuário não encontrado.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Create user success.")
    void insertSuccess() {
        Mockito.when(mapStructMapper.toUserEntity(any())).thenReturn(user);
        Mockito.when(userRepository.save(any())).thenReturn(user);
        User user = userService.insert(userDTO);
        assertNotNull(user);
        assertEquals(User.class, user.getClass());
        assertEquals(ID, user.getId());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
    }

    @Test
    @DisplayName("Create user sem success login ja existe.")
    void insertNoSuccessLoginExist() {
        Mockito.when(userRepository.findUserByLogin(anyString())).thenReturn(optionalUser);
        try {
            User user = userService.insert(userDTO);
        }catch (Exception e){
            assertEquals(BusinessException.class, e.getClass());
            assertEquals("Login already exists.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Create user sem success email ja existe.")
    void insertNoSuccessEmailExist() {
        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(optionalUser);
        try {
            userDTO.setLogin("LoginNotExist");
            User user = userService.insert(userDTO);

        }catch (Exception e){
            assertEquals(BusinessException.class, e.getClass());
            assertEquals("Email already exists.", e.getMessage());
        }
    }

    @Test
    @DisplayName("User atualizado com success.")
    void updateSuccess() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);
        Mockito.when(userRepository.save(any())).thenReturn(user);
        User user = userService.update(userDTO);
        assertNotNull(user);
        assertEquals(User.class, user.getClass());
        assertEquals(ID, user.getId());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
    }

    @Test
    @DisplayName("User não atualizado email ja existe.")
    void updateNoSuccessEmailExist() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);
        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(optionalUser);
        try {
            optionalUser.get().setId(2L);
            optionalUser.get().setEmail("all@all.com");
            User user = userService.update(userDTO);

        }catch (Exception e){
            assertEquals(BusinessException.class, e.getClass());
            assertEquals("Email already exists.", e.getMessage());
        }
    }

    @Test
    @DisplayName("User deletado com successo.")
    void deleteSuccess() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyLong());
        userRepository.deleteById(ID);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    @DisplayName("User não pode ser deletado")
    void deleteNotSuccess() {
        try{
            Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(null);
            userRepository.deleteById(ID);

        }catch (Exception e){
            assertEquals(BusinessException.class, e.getClass());
            assertEquals("User não encontrato.", e.getMessage());
        }

    }

    private void createUser(){
        user = new User(ID, FIRST_NAME, LAST_NAME, EMAIL,
                BIRTHDAY, LOGIN, PASSWORD, PHONE, CREATED, LAST_LOGIN);

        userDTO = new UserDTO(ID, FIRST_NAME, LAST_NAME,EMAIL,
                BIRTHDAY, LOGIN, PASSWORD, PHONE, CREATED, LAST_LOGIN, CAR_DTO_LIST);

        optionalUser = Optional.of(new User(ID, FIRST_NAME, LAST_NAME,EMAIL,
                BIRTHDAY, LOGIN, PASSWORD, PHONE, CREATED, LAST_LOGIN));

        ;   }
}