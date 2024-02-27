package com.desafiopitang.api.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CredentialDTO implements Serializable {

    private static  final  long serialVersionUID = 1L;

    private String login;

    private String password;

}

