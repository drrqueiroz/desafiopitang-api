package com.desafiopitang.api.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CredenciasDTO implements Serializable {

    private static  final  long serialVersionUID = 1L;

    private String usuario;

    private String senha;

}

