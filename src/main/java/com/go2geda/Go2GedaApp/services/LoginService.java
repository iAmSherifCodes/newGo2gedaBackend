//package com.go2geda.Go2GedaApp.services;
//
//
//import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
//import com.go2geda.Go2GedaApp.repositories.BasicInformationRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service @AllArgsConstructor
//public class LoginService {
//    private final BasicInformationRepository basicInformationRepository;
//
//    public RegisterUserResponse Login(String email, String password){
//        String emailRequest = email.strip();
//
//        basicInformationRepository.findBasicInformationByEmailAndPassword(emailRequest, password);
//
//    }
//}
