package com.go2geda.Go2GedaApp.controller;

import com.go2geda.Go2GedaApp.dtos.request.LoginRequest;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.Go2gedaBaseException;
import com.go2geda.Go2GedaApp.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.go2geda.Go2GedaApp.exceptions.ExceptionMessage.USER_WITH_EMAIL_NOT_FOUND;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/go2geda")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

//    @PostMapping("/login")
//    public ResponseEntity<RegisterUserResponse> login(@RequestBody LoginRequest loginRequest){
//        try{
//            return new ResponseEntity<>(loginService.login(loginRequest), HttpStatus.OK);
//        }catch (RuntimeException e){
//            throw new Go2gedaBaseException(USER_WITH_EMAIL_NOT_FOUND.name());
//        }
//    }

}
