package com.go2geda.Go2GedaApp.controller;

import com.go2geda.Go2GedaApp.data.models.Trip;
import com.go2geda.Go2GedaApp.dtos.request.*;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;
import com.go2geda.Go2GedaApp.exceptions.UserNotFound;
import com.go2geda.Go2GedaApp.services.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/go2geda/driver")
@CrossOrigin("*")
@AllArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/registerDriver")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody DriverRegisterUserRequest registerUserRequest) {
        return new ResponseEntity<>(driverService.register(registerUserRequest), HttpStatus.OK);
    }

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