package com.go2geda.Go2GedaApp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminRegistrationRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String email;
}
