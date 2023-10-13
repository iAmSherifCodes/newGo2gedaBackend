package com.go2geda.Go2GedaApp.services;


import com.go2geda.Go2GedaApp.data.models.Commuter;
import com.go2geda.Go2GedaApp.data.models.Driver;
import com.go2geda.Go2GedaApp.data.models.Role;
import com.go2geda.Go2GedaApp.data.models.User;
import com.go2geda.Go2GedaApp.dtos.request.LoginRequest;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.Go2gedaBaseException;
import com.go2geda.Go2GedaApp.repositories.CommuterRepository;
import com.go2geda.Go2GedaApp.repositories.DriverRepository;
import com.go2geda.Go2GedaApp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.go2geda.Go2GedaApp.exceptions.ExceptionMessage.*;

@Service @AllArgsConstructor
public class LoginService {
    private final DriverRepository driverRepository;
    private final CommuterRepository commuterRepository;
    private final UserRepository userRepository;

    public RegisterUserResponse Login(LoginRequest loginRequest){

        String emailRequest = loginRequest.getEmail().strip().toLowerCase();
        String password = loginRequest.getPassword();

        RegisterUserResponse response =  new RegisterUserResponse();

        User foundUser = userRepository.findByEmail(emailRequest).orElseThrow(()-> new Go2gedaBaseException(USER_WITH_EMAIL_NOT_FOUND.name()+ emailRequest));

        if (foundUser.getBasicInformation().getEmail().equals(emailRequest) && foundUser.getBasicInformation().getPassword().equals(password)){
            Role userRole = foundUser.getRole();

            if (userRole.equals(Role.DRIVER)){
                Driver foundDriver = driverRepository.findDriverByEmail(foundUser.getBasicInformation().getEmail()).orElseThrow(()-> new Go2gedaBaseException(USER_WITH_EMAIL_NOT_FOUND.name()));;
                response.setId(foundDriver.getId());
                response.setMessage(LOGIN_SUCCESSFUL.name());
            }
            if (userRole.equals(Role.COMMUTER)){
                Commuter foundCommuter = commuterRepository.findCommuterByEmail(foundUser.getBasicInformation().getEmail()).orElseThrow(()-> new Go2gedaBaseException(USER_WITH_EMAIL_NOT_FOUND.name()));;
                response.setId(foundCommuter.getId());
                response.setMessage(LOGIN_SUCCESSFUL.name());
            }

            return response;
        }else{
            response.setMessage(WRONG_CREDENTIALS.name());
        }
        return response;
    }
}
