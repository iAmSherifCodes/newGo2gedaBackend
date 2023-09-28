package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.*;
import com.go2geda.Go2GedaApp.dtos.request.AddressVerificationRequest;
import com.go2geda.Go2GedaApp.dtos.request.CommuterRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.request.EmailSenderRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.UserNotFound;
import com.go2geda.Go2GedaApp.repositories.CommuterRepository;
import com.go2geda.Go2GedaApp.utils.BuildEmailRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.go2geda.Go2GedaApp.dtos.response.ResponseMessage.REGISTRATION_SUCCESSFUL;
import static com.go2geda.Go2GedaApp.dtos.response.ResponseMessage.VERIFIED_SUCCESSFUL;
import static com.go2geda.Go2GedaApp.exceptions.ExceptionMessage.USER_NOT_FOUND;


@Service @AllArgsConstructor
public class Go2gedaCommuterService implements CommuterService{

    private final BuildEmailRequest buildEmailRequest;
    private final CommuterRepository commuterRepository;
    private final MailService mailService;
    @Override
    public RegisterUserResponse register(CommuterRegisterUserRequest request) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String email = request.getEmail();
        String password = request.getPassword();
        String phoneNumber = request.getPhoneNumber();

        User newUser = new User();
        BasicInformation basicInformation = new BasicInformation();

        basicInformation.setFirstName(firstName);
        basicInformation.setLastName(lastName);
        basicInformation.setEmail(email);
        basicInformation.setPassword(password);
        basicInformation.setPhoneNumber(phoneNumber);

        newUser.setRole(Role.COMMUTER);
        newUser.setBasicInformation(basicInformation);

        Commuter newCommuter = new Commuter();
        newCommuter.setUser(newUser);

        commuterRepository.save(newCommuter);

        EmailSenderRequest emailSenderRequest = buildEmailRequest.buildEmailRequest(newUser);
        mailService.send(emailSenderRequest);

        RegisterUserResponse response = new RegisterUserResponse();
        response.setMessage(REGISTRATION_SUCCESSFUL.name());

        return response;
    }

    @Override
    public OkResponse verifyAddress(AddressVerificationRequest addressVerificationRequest, String email) {
        String localGovernment = addressVerificationRequest.getLocalGovernment();
        String state = addressVerificationRequest.getState();
        String homeAddress = addressVerificationRequest.getHomeAddress();

        Address newAddress = new Address();
        newAddress.setHomeAddress(homeAddress);
        newAddress.setState(state);
        newAddress.setLocalGovernment(localGovernment);

        Commuter foundDriver = findCommuterByEmail(email);
        foundDriver.getUser().setAddress(newAddress);

        commuterRepository.save(foundDriver);

        OkResponse response = new OkResponse();
        response.setMessage(VERIFIED_SUCCESSFUL.name());

        return response;
    }

    private Commuter findCommuterByEmail(String email){
        try {
            return commuterRepository.findByUserBasicInformationEmail(email).orElseThrow(()-> new UserNotFound(USER_NOT_FOUND.name()));
        } catch (UserNotFound e) {
            throw new RuntimeException(e);
        }

    }
}
