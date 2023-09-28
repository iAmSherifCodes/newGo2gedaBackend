package com.go2geda.Go2GedaApp.services;


import com.go2geda.Go2GedaApp.dtos.request.AddressVerificationRequest;
import com.go2geda.Go2GedaApp.dtos.request.CommuterRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;

public interface CommuterService {
    RegisterUserResponse register(CommuterRegisterUserRequest request);
    OkResponse verifyAddress(AddressVerificationRequest addressVerificationRequest, String email);

}