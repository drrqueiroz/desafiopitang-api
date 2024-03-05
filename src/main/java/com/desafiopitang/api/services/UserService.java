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
     * @param pLogin
     * @return
     */
    public User findByLogin(String pLogin);
    /**
     *
     * @return
     */
    public UserMeDTO findByMe(String pLogin);

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
     * @param pLogin
     * @return
     */
    public User updateRegistLastLogin(String pLogin);

    /**
     *
     * @param id
     * @return
     */
    public void delete(long id);
}
