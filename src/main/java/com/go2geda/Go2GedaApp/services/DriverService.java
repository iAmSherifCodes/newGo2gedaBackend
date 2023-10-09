package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.Driver;
import com.go2geda.Go2GedaApp.dtos.request.AccountDetailsVerificationRequest;
import com.go2geda.Go2GedaApp.dtos.request.AddressVerificationRequest;
import com.go2geda.Go2GedaApp.dtos.request.DriverLicenceVerificationRequest;
import com.go2geda.Go2GedaApp.dtos.request.DriverRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.UserNotFound;
import org.springframework.stereotype.Service;


public interface DriverService {
    RegisterUserResponse register(DriverRegisterUserRequest request);
    Driver findDriverByEmail(String email) throws UserNotFound;
    OkResponse verifyAddress(AddressVerificationRequest addressVerificationRequest, String email) throws UserNotFound;
    OkResponse verifyDriverAccountDetails(AccountDetailsVerificationRequest accountDetailsVerificationRequest, String email) throws UserNotFound;
    OkResponse verifyDriverLicense(DriverLicenceVerificationRequest driverLicenceVerificationRequest);
}