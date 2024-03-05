package com.desafiopitang.api.controllers;


import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.UserDTO;
import com.desafiopitang.api.dto.UserMeDTO;
import com.desafiopitang.api.mapper.MapStructMapper;
import com.desafiopitang.api.security.JwtTokenUtil;
import com.desafiopitang.api.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private MapStructMapper mapStructMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDTO>> findAll() {

        List<User> listUser = userService.findAll();
        List<UserDTO>  listDTO = listUser.stream().map(x -> mapStructMapper.toUserDTO(x)).collect(Collectors.toList());
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserMeDTO> findByMe(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String username = jwtTokenUtil.getUsername(header.substring(7));
        UserMeDTO userMeDTO = userService.findByMe(username);
        return new ResponseEntity<>(userMeDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(mapStructMapper.toUserDTO(user) , HttpStatus.OK);
    }

    @Transactional
    @PostMapping(path = "/users")
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO userDTO) {

        User user = userService.insert(userDTO);
        return new ResponseEntity<>(mapStructMapper.toUserDTO(user) , HttpStatus.CREATED);
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        User user = userService.update(userDTO);
        return new ResponseEntity<>(mapStructMapper.toUserDTO(user) , HttpStatus.OK);

    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        userService.delete(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "Registro deletado com sucesso.");
        map.put("status", HttpStatus.OK.value());
        return new ResponseEntity<Object>(map, HttpStatus.OK);

    }

}

