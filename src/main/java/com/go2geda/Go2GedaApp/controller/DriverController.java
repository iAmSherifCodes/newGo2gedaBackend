package com.go2geda.Go2GedaApp.controller;

import com.go2geda.Go2GedaApp.data.models.Trip;
import com.go2geda.Go2GedaApp.dtos.request.*;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.Go2gedaBaseException;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;
import com.go2geda.Go2GedaApp.exceptions.UserNotFound;
import com.go2geda.Go2GedaApp.services.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.go2geda.Go2GedaApp.exceptions.ExceptionMessage.EMAIL_ALREADY_EXIST;

@RestController
@RequestMapping("/api/v1/go2geda/driver")
@CrossOrigin("*")
@AllArgsConstructor
public class DriverController {
    private final DriverService driverService;
    @PostMapping("/registerDriver")
    public ResponseEntity<?> register(@RequestBody DriverRegisterUserRequest registerUserRequest) {
//        try{
            return new ResponseEntity<>(driverService.register(registerUserRequest), HttpStatus.OK);
//        } catch (Go2gedaBaseException e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    // "/registerDriver" , "/verifyAddress", "/verifyAccountDetails/{from}", "/verifyDriverLicense/{from}"

    @PostMapping("/verifyAddress")
    public ResponseEntity<OkResponse> verifyAddress(@RequestBody AddressVerificationRequest addressVerificationRequest, String email) {
        try {
            return new ResponseEntity<>(driverService.verifyAddress(addressVerificationRequest, email), HttpStatus.FOUND);
        } catch (UserNotFound e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/verifyAccountDetails/{from}")
    public ResponseEntity<OkResponse> verifyAccountDetails(@RequestBody AddressVerificationRequest addressVerificationRequest, String email, @PathVariable String from) {
        try {
            return new ResponseEntity<>(driverService.verifyAddress(addressVerificationRequest, email), HttpStatus.FOUND);
        } catch (UserNotFound e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/verifyDriverLicense/{from}")
    public ResponseEntity<OkResponse> verifyDriverLicense(@RequestBody DriverLicenceVerificationRequest driverLicenceVerificationRequest, String email, @PathVariable String from) {
        return new ResponseEntity<>(driverService.verifyDriverLicense(driverLicenceVerificationRequest), HttpStatus.FOUND);
    }
}