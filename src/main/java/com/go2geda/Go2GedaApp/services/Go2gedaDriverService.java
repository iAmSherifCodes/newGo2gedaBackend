package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.*;
import com.go2geda.Go2GedaApp.dtos.request.*;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.Go2gedaBaseException;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;
import com.go2geda.Go2GedaApp.exceptions.UserDoesNotExist;
import com.go2geda.Go2GedaApp.exceptions.UserNotFound;
import com.go2geda.Go2GedaApp.repositories.BasicInformationRepository;
import com.go2geda.Go2GedaApp.repositories.DriverRepository;
import com.go2geda.Go2GedaApp.repositories.TripRepository;
import com.go2geda.Go2GedaApp.utils.BuildEmailRequest;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.go2geda.Go2GedaApp.dtos.response.ResponseMessage.REGISTRATION_SUCCESSFUL;
import static com.go2geda.Go2GedaApp.dtos.response.ResponseMessage.VERIFIED_SUCCESSFUL;
import static com.go2geda.Go2GedaApp.exceptions.ExceptionMessage.EMAIL_ALREADY_EXIST;
import static com.go2geda.Go2GedaApp.exceptions.ExceptionMessage.USER_NOT_FOUND;
import static com.go2geda.Go2GedaApp.utils.AppUtils.UPLOAD_SUCCESSFUL;
import static com.go2geda.Go2GedaApp.utils.AppUtils.VERIFICATION_SUCCESSFUL;

@AllArgsConstructor
@Service
public class Go2gedaDriverService implements  DriverService{

    private final DriverRepository driverRepository;
    private final CloudService cloudService;

    private final BuildEmailRequest buildEmailRequest;
    private final MailService mailService;
    @Override
    public RegisterUserResponse register(DriverRegisterUserRequest request) {
//        try{

            String email = request.getEmail();
        if (emailExist(email)) throw new Go2gedaBaseException(EMAIL_ALREADY_EXIST.name());

        String firstName = request.getFirstName();
            String lastName = request.getLastName();

            String password = request.getPassword();
            String phoneNumber = request.getPhoneNumber();

            User newUser = new User();
            newUser.setRole(Role.DRIVER);

            BasicInformation basicInformation = new BasicInformation();

            basicInformation.setFirstName(firstName);
            basicInformation.setLastName(lastName);
            basicInformation.setEmail(email);
            basicInformation.setPassword(password);
            basicInformation.setPhoneNumber(phoneNumber);

            newUser.setBasicInformation(basicInformation);

            Driver newDriver = new Driver();
            newDriver.setUser(newUser);


            EmailSenderRequest emailSenderRequest = buildEmailRequest.buildEmailRequest(newUser);
            mailService.send(emailSenderRequest);


            Driver savedDriver = driverRepository.save(newDriver);

            RegisterUserResponse response = new RegisterUserResponse();
            response.setMessage(REGISTRATION_SUCCESSFUL.name());
            response.setId(savedDriver.getId());
            response.setEmail(savedDriver.getUser().getBasicInformation().getEmail());
            return response;
//        }catch(DataIntegrityViolationException e){
//            throw new Go2gedaBaseException(EMAIL_ALREADY_EXIST.name());
//        }
    }

    public boolean emailExist(String email){
        return driverRepository.findDriverByEmail(email).isPresent();
    }


    @Override
    public Driver findDriverByEmail(String email) throws UserNotFound {
        return driverRepository.findDriverByEmail(email).orElseThrow(()-> new UserNotFound(USER_NOT_FOUND.name()));
    }

    @Override
    public OkResponse verifyAddress(AddressVerificationRequest addressVerificationRequest, String email) throws UserNotFound {
        String localGovernment = addressVerificationRequest.getLocalGovernment();
        String state = addressVerificationRequest.getState();
        String homeAddress = addressVerificationRequest.getHomeAddress();

        Address newAddress = new Address();
        newAddress.setHomeAddress(homeAddress);
        newAddress.setState(state);
        newAddress.setLocalGovernment(localGovernment);

        Driver foundDriver = findDriverByEmail(email);
        foundDriver.getUser().setAddress(newAddress);

        driverRepository.save(foundDriver);

        OkResponse response = new OkResponse();
        response.setMessage(VERIFIED_SUCCESSFUL.name());

        return response;
    }

    @Override
    public OkResponse verifyDriverAccountDetails(AccountDetailsVerificationRequest accountDetailsVerificationRequest, String email) throws UserNotFound {

        Driver foundDriver = findDriverByEmail(email);

        DriverInformation driverInformation = new DriverInformation();
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAccountNUmber(accountDetailsVerificationRequest.getAccountNUmber());
        accountDetails.setBankName(accountDetailsVerificationRequest.getBankName());
        accountDetails.setBankVerificationNUmber(accountDetailsVerificationRequest.getBankVerificationNUmber());


        driverInformation.setAccountDetails(accountDetails);
        foundDriver.setDriverInformation(driverInformation);

        Driver saved = driverRepository.save(foundDriver);


        OkResponse okResponse = new OkResponse();
        okResponse.setMessage(VERIFICATION_SUCCESSFUL);
        return okResponse;
    }

    @Override
    public OkResponse verifyDriverLicense(DriverLicenceVerificationRequest driverLicenceVerificationRequest) {
        MultipartFile frontLicensePicture = driverLicenceVerificationRequest.getFrontPicture();
        MultipartFile backLicensePicture = driverLicenceVerificationRequest.getBackPicture();

        cloudService.upload(frontLicensePicture, "driver-license");
        cloudService.upload(backLicensePicture, "driver-license");

        OkResponse response = new OkResponse();
        response.setMessage(UPLOAD_SUCCESSFUL);

        return response;
    }
}
