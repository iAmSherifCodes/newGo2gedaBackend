package com.go2geda.Go2GedaApp.dtos.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserResponse {
    private String message;
    private Long id;
    private  String firstName;
    private String lastName;
    private String  email;
    private String phoneNumber;
    private String password;
}
