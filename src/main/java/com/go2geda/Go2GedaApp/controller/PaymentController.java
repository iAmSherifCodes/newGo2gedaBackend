package com.go2geda.Go2GedaApp.controller;

import com.cloudinary.api.exceptions.AlreadyExists;
import com.go2geda.Go2GedaApp.data.models.PayStackCustomer;
import com.go2geda.Go2GedaApp.data.models.Wallet;
import com.go2geda.Go2GedaApp.dtos.request.CreatePayStackCustomerRequest;
import com.go2geda.Go2GedaApp.dtos.request.CreateVirtualAccountRequest;
import com.go2geda.Go2GedaApp.dtos.request.CreateWalletRequest;
import com.go2geda.Go2GedaApp.dtos.request.PayStackValidationRequest;
import com.go2geda.Go2GedaApp.dtos.response.*;
import com.go2geda.Go2GedaApp.exceptions.BadRequestException;
import com.go2geda.Go2GedaApp.exceptions.ResourceNotFoundEception;
import com.go2geda.Go2GedaApp.exceptions.UserAlreadyExist;
import com.go2geda.Go2GedaApp.exceptions.UserDoesNotExist;
import com.go2geda.Go2GedaApp.services.PaymentService;
import com.go2geda.Go2GedaApp.services.VirtualAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cloudinary.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/wallet")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final VirtualAccountService virtualAccountService;

    @PostMapping("/create")
    public ResponseEntity<?> createWallet(@RequestBody CreateWalletRequest walletRequest) {
        log.info(String.valueOf(walletRequest.getUserId()));

        try {
            Wallet wallet = paymentService.createWallet(walletRequest.getUserId(), walletRequest.getAccountNumber(),
                    walletRequest.getBankCode(),
                    walletRequest.getWalletBvn(), walletRequest.getWalletPin(), walletRequest.getBank());
            return new ResponseEntity<>(
                    new WalletBaseResponse(true, HttpStatus.CREATED.value(), "successful",
                            LocalDateTime.now(), wallet)
                    , HttpStatus.CREATED);
        } catch (BadRequestException | ResourceNotFoundEception | AlreadyExists e) {
            return new ResponseEntity<>(
                    new BaseResponse(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                            LocalDateTime.now())
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/link/{flightId}")
    public ResponseEntity<?> getPaymentLink(@PathVariable Long flightId) {
        try {
            String paymentLink = paymentService.getPaymentLink(flightId);
            return new ResponseEntity<>(
                    new PaymentLinkBaseResponse(true, HttpStatus.OK.value(), "successful",
                            LocalDateTime.now(), paymentLink)
                    , HttpStatus.OK);
        } catch (ResourceNotFoundEception e) {
            return new ResponseEntity<>(
                    new BaseResponse(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                            LocalDateTime.now())
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllBank")
    public ResponseEntity<?> getALLBankCodes() {
        try {

            JSONObject getAllBankPayment = paymentService.getAllBankCodes();
            return new ResponseEntity<>(
                    new BankResponse(true, HttpStatus.OK.value(), "successful",
                            LocalDateTime.now(), getAllBankPayment)
                    , HttpStatus.OK);
        } catch (ResourceNotFoundEception e) {
            return new ResponseEntity<>(
                    new BaseResponse(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                            LocalDateTime.now())
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/fundWallet/{userId}")
    public ResponseEntity<?> fundWallet(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        try {
            Wallet wallet = paymentService.addFundToWallet(userId, amount);
            return new ResponseEntity<>(
                    new WalletBaseResponse(true, HttpStatus.CREATED.value(), "successful",
                            LocalDateTime.now(), wallet)
                    , HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(
                    new BaseResponse(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                            LocalDateTime.now())
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createCustomer/{userId}")
    public ResponseEntity<?> createCustomer(@RequestBody CreatePayStackCustomerRequest createPayStackCustomerRequest, @PathVariable Long userId) {
        try {
            CreatePayStackCustomerResponse createPayStackCustomerResponse = paymentService.createPayStackCustomerRequestHttpEntity(createPayStackCustomerRequest, userId);
            return new ResponseEntity<>(
                    new CreateCustomerResponse(true, HttpStatus.CREATED.value(), "successful",
                            LocalDateTime.now(), createPayStackCustomerResponse)
                    , HttpStatus.CREATED);
        } catch (ResourceNotFoundEception | UserDoesNotExist e) {
            return new ResponseEntity<>(
                    new BaseResponse(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                            LocalDateTime.now())
                    , HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/validate/{customerCode}")
    public ResponseEntity<?> validatePayStackCustomer(@RequestBody PayStackValidationRequest paystackCustomerValidationRequest, @PathVariable String customerCode) {
        try {
            PayStackValidationResponse response = paymentService.validation(paystackCustomerValidationRequest, customerCode);
            return new ResponseEntity<>(new ValidationResponse(true, HttpStatus.CREATED.value(), "successful",
                    LocalDateTime.now(), response)
                    , HttpStatus.CREATED);
        } catch (HttpClientErrorException.BadRequest e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(new BaseResponse(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    LocalDateTime.now())
                    , HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createVirtualAccount(@RequestBody CreateVirtualAccountRequest virtualAccountRequest,
                                                   @PathVariable Long userId, @RequestParam String walletPin) {
        try{
            VirtualAccountResponse response = virtualAccountService.createVirtualAccount(virtualAccountRequest,userId, walletPin);
            return new ResponseEntity<>(new VirtualBaseResponse(true, HttpStatus.CREATED.value(), "successful",
                    LocalDateTime.now(), response)
                    , HttpStatus.CREATED);
        } catch (HttpClientErrorException.BadRequest | UserAlreadyExist |AlreadyExists |ResourceNotFoundEception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(new BaseResponse(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                    LocalDateTime.now())
                    , HttpStatus.BAD_REQUEST);

        }
    }
}