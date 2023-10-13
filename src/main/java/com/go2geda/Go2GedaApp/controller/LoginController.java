package com.go2geda.Go2GedaApp.controller;

import com.go2geda.Go2GedaApp.dtos.request.LoginRequest;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/go2geda")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<RegisterUserResponse> login(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(loginService.Login(loginRequest), HttpStatus.FOUND);
    }

}
