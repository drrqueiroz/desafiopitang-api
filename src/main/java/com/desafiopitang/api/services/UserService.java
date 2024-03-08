package com.desafiopitang.api.services;

import com.desafiopitang.api.domain.User;
import com.desafiopitang.api.dto.UserDTO;
import com.desafiopitang.api.dto.UserMeDTO;

import java.util.List;

public interface UserService {
    /**
     * Consultar todos usuários cadastrados
     * @return
     */
    public List<User> findAll();

    /**
     * Consultar usuário pelo login
     * @param pLogin
     * @return
     */
    public User findByLogin(String pLogin);
    /**
     * Consultar todas as informacoes relacionadas ao login do usuário
     * @return
     */
    public UserMeDTO findByMe(String pLogin);

    /**
     * Consultar usuário pelo id
     * @param id
     * @return
     */
    public User findById(long id);
    /**
     * Salvar um usuário
     * @param userDTO
     * @return
     */
    public User insert(UserDTO userDTO);

    /**
     * Atualizar um usuário
     * @param userDTO
     * @return
     */
    public User update(UserDTO userDTO);

    /**
     * Atualizar o registro data do ultimo login
     * @param pLogin
     */
    public User updateRegistLastLogin(String pLogin);

    /**
     * Deletar usuário
     * @param id
     * @return
     */
    public void delete(long id);
}
