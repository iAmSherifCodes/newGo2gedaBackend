package com.go2geda.Go2GedaApp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DriverRegisterUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
}
