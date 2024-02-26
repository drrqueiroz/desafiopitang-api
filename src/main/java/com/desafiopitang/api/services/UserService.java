package com.desafiopitang.api.services;

import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.UserDTO;
import com.desafiopitang.api.dto.UserMeDTO;

import java.util.List;

public interface UserService {
    /**
     *
     * @return
     */
    public List<User> findAll();

    /**
     *
     * @return
     */
    public UserMeDTO findByMe();

    /**
     *
     * @param id
     * @return
     */
    public User findById(long id);
    /**
     *
     * @param userDTO
     * @return
     */
    public User insert(UserDTO userDTO);

    /**
     *
     * @param userDTO
     * @return
     */
    public User update(UserDTO userDTO);

    /**
     *
     * @param id
     * @return
     */
    public void delete(long id);
}
