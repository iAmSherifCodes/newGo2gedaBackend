package com.go2geda.Go2GedaApp.services;

import com.cloudinary.api.exceptions.AlreadyExists;
import com.go2geda.Go2GedaApp.configs.PayStackConfig;
import com.go2geda.Go2GedaApp.data.models.PayStackCustomer;
import com.go2geda.Go2GedaApp.data.models.User;
import com.go2geda.Go2GedaApp.data.models.VirtualAccount;
import com.go2geda.Go2GedaApp.data.models.Wallet;
import com.go2geda.Go2GedaApp.dtos.request.CreateVirtualAccountRequest;
import com.go2geda.Go2GedaApp.dtos.request.PayStackValidationRequest;
import com.go2geda.Go2GedaApp.dtos.response.PayStackValidationResponse;
import com.go2geda.Go2GedaApp.dtos.response.VirtualAccountBankResponse;
import com.go2geda.Go2GedaApp.dtos.response.VirtualAccountDataResponse;
import com.go2geda.Go2GedaApp.dtos.response.VirtualAccountResponse;
import com.go2geda.Go2GedaApp.exceptions.BadRequestException;
import com.go2geda.Go2GedaApp.exceptions.ResourceNotFoundEception;
import com.go2geda.Go2GedaApp.exceptions.UserAlreadyExist;
import com.go2geda.Go2GedaApp.repositories.PayStackCustomerRepository;
import com.go2geda.Go2GedaApp.repositories.UserRepository;
import com.go2geda.Go2GedaApp.repositories.VirtualAccountRepository;
import com.go2geda.Go2GedaApp.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
@Service
@AllArgsConstructor
@Slf4j
public class VirtualAccountImpl implements VirtualAccountService {

    private final VirtualAccountRepository virtualAccountRepository;
    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final PayStackConfig payStackConfig;

    private final PayStackCustomerRepository payStackCustomerRepository;

    private final WalletRepository walletRepository;
    @Override
    public VirtualAccountResponse createVirtualAccount(CreateVirtualAccountRequest createVirtualAccountRequest, Long userId, String walletPin) throws AlreadyExists {
        Optional<User> user = userRepository.findById(userId);
        log.info(String.valueOf(userId));
                if (user.isPresent()) {
                Optional<PayStackCustomer> payStackCustomer = payStackCustomerRepository.findByCustomerCode(createVirtualAccountRequest.getCustomer());

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        header.set("Authorization", "Bearer " + "sk_test_309ce50eee2a1b9605286948cc4418a5894f87fc");

        Optional<Wallet> wallet = walletRepository.findWalletByUserId(userId);
        if (wallet.isEmpty()) {
            org.springframework.http.HttpEntity<CreateVirtualAccountRequest> entity =
        new org.springframework.http.HttpEntity<>(createVirtualAccountRequest, header);
        ResponseEntity<VirtualAccountResponse> response = restTemplate.postForEntity(payStackConfig.getCreateVirtualAccountUrl(), entity, VirtualAccountResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info(response.getBody().getStatus());
            log.info(response.getBody().getMessage());
            log.info(response.getBody().getData().getAccount_number() +" "+ response.getBody().getData().getAccount_name());
        saveVirtualAccount(user.get().getId(), Objects.requireNonNull(response.getBody()).getData().getAccount_name(), Objects.requireNonNull(response.getBody()).getData().getAccount_number(), Objects.requireNonNull(response.getBody()).getData().isActive(), Objects.requireNonNull(response.getBody()).getData().getCreated_at(), Objects.requireNonNull(response.getBody()).getData().getUpdated_at());
//        paymentService.createWallet(user.get().getId(), response.getBody().getData().getAccount_number(),
//                response.getBody().getData().getAccount_name(),
//                payStackCustomer.get().getBvn(),"wema_bank",walletPin);
        return response.getBody();
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
        throw new BadRequestException(Objects.requireNonNull(response.getBody()).getMessage());
        } else {
        throw new ResourceNotFoundEception(Objects.requireNonNull(response.getBody()).getMessage());
        }
        } else {
        throw new UserAlreadyExist("User already has a wallet");
        }
        } else {
        throw new ResourceNotFoundEception("User does not exist");
        }


    }
    @Override
    public VirtualAccount saveVirtualAccount(Long userId, String accountName,
                                             String accountNumber,
                                             boolean active, String createdAt, String upDatedAt) {
        VirtualAccount virtualAccount = new VirtualAccount();
        virtualAccount.setUserId(userId);
        virtualAccount.setAccountName(accountName);
        virtualAccount.setAccountName(accountNumber);
//        virtualAccount.setBank(bank);
        virtualAccount.setActive(active);
        virtualAccount.setCreatedAt(createdAt);
        virtualAccount.setUpdatedAt(upDatedAt);
        return virtualAccountRepository.save(virtualAccount);
    }
}


//
//    Optional<User> user = userRepository.findById(userId);
//












//-------------------------------------------------------MAIN-----------------------------



//  if (user.isPresent()) {
//          Optional<PayStackCustomer> payStackCustomer = payStackCustomerRepository.findByCustomerCode(createVirtualAccountRequest.getCustomer());
//        RestTemplate restTemplate = new RestTemplate();
//        org.springframework.http.HttpHeaders header = new org.springframework.http.HttpHeaders();
//        header.setContentType(MediaType.APPLICATION_JSON);
//        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        header.set("Authorization", "Bearer " + payStackConfig.getSecretKey());
//        Optional<Wallet> wallet = walletRepository.findWalletByUserId(userId);
//        if (wallet.isEmpty()) {
//        org.springframework.http.HttpEntity<CreateVirtualAccountRequest> entity =
//        new org.springframework.http.HttpEntity<>(createVirtualAccountRequest, header);
//        ResponseEntity<VirtualAccountResponse> response = restTemplate.postForEntity(payStackConfig.getCustomerValidationUrl(), entity, VirtualAccountResponse.class);
////                log.info(String.valueOf(response));
//        if (response.getStatusCode() == HttpStatus.OK) {
//        saveVirtualAccount(user.get().getId(), Objects.requireNonNull(response.getBody()).getData().getAccount_name(),
//        Objects.requireNonNull(response.getBody()).getData().getAccount_number(),
//        Objects.requireNonNull(response.getBody())
//        .getData().getBank().getName(), Objects.requireNonNull(response.getBody()).getData().isActive(), Objects.requireNonNull(response.getBody()).getData().getCreated_at(), Objects.requireNonNull(response.getBody()).getData().getUpdated_at());
//        paymentService.createWallet(user.get().getId(), response.getBody().getData().getAccount_number(),
//        response.getBody().getData().getAccount_name(), response.getBody().getData().getBank().getName(), payStackCustomer.get().getBvn());
//        return response.getBody();
//        }
//        else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
//        throw new BadRequestException(Objects.requireNonNull(response.getBody()).getMessage());
//        }
//
//        } else {
//        throw new UserAlreadyExist("User already has a wallet");
//        }
//        } else {
//        throw new ResourceNotFoundEception("User does not exist");
//
//        }
//        }
//        }
//




















//    private final PaystackConfig paystackConfig;
//    private final VirtualAccountRepository virtualAccountRepository;
//    private final UserService userService;
//    private final WalletService walletService;
//    private final PayStackCustomerRepository paystackCustomerRepository;
//    private final EmailVerificationService emailVerificationService;
//
//    @Override
//    public String createVirtualAccount(CreateVirtualAccountRequest createVirtualAccountRequest, Long userId, Long walletPin) throws HttpClientErrorException, MessagingException, IOException {
//        Optional<CivilUser> user = userService.findById(userId);
//        BigDecimal govelyWalletTotalBalance = new BigDecimal(0);
//        BigDecimal walletActualBalance = new BigDecimal(0);
////        BigDecimal creditLimit = new BigDecimal(1000);
//        BigDecimal debitLimit = new BigDecimal(0);
//        if (user.isPresent()) {
//            Optional<PayStackCustomer> payStackCustomer = paystackCustomerRepository.findByCustomerCode(createVirtualAccountRequest.getCustomer());
//            RestTemplate restTemplate = new RestTemplate();
//            HttpHeaders header = new HttpHeaders();
//            header.setContentType(MediaType.APPLICATION_JSON);
//            header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//            header.set("Authorization", "Bearer " + paystackConfig.getSecretKey());
//
//            Optional<Wallet> wallet = walletService.findWalletByUserId(userId);
//            if (wallet.isEmpty()) {
//                HttpEntity<CreateVirtualAccountRequest> entity = new HttpEntity<CreateVirtualAccountRequest>(createVirtualAccountRequest, header);
//                ResponseEntity<VirtualAccountResponse> response = restTemplate.postForEntity(paystackConfig.getCreateVirtualAccountUrl(), entity, VirtualAccountResponse.class);
//                if (response.getStatusCode() == HttpStatus.OK) {
//                    saveVirtualAccount(user.get().getId(), Objects.requireNonNull(response.getBody()).getData().getAccount_name(), Objects.requireNonNull(response.getBody()).getData().getAccount_number(), Objects.requireNonNull(response.getBody()).getData().getBank().getName(), Objects.requireNonNull(response.getBody()).getData().isActive(), Objects.requireNonNull(response.getBody()).getData().getCreated_at(), Objects.requireNonNull(response.getBody()).getData().getUpdated_at());
//                    walletService.createWallet(user.get().getId(), response.getBody().getData().getAccount_number(), response.getBody().getData().getAccount_name(), response.getBody().getData().getBank().getName(), payStackCustomer.get().getBvn(), govelyWalletTotalBalance, walletActualBalance, walletService.getCreditLimit(user.get().getId()), debitLimit, LocalDate.now(), LocalDate.now(), walletPin);
//                    emailVerificationService.sendBankAccountOpeningMail(user.get(), Objects.requireNonNull(response.getBody()).getData().getAccount_number(), response.getBody().getData().getBank().getName(), response.getBody().getData().getAccount_name(), "Transfer / Payment link / Card", walletService.getCreditLimit(user.get().getId()));
//                    return response.getBody().getMessage();
//                } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
//                    throw new BadRequestException(Objects.requireNonNull(response.getBody()).getMessage());
//                } else {
//                    throw new ResourceNotFound(Objects.requireNonNull(response.getBody()).getMessage());
//                }
//            } else {
//                throw new ResourceAlreadyExist("User already has a wallet");
//            }
//        } else {
//            throw new ResourceNotFound("User does not exist");
//        }
//    }
//
//
//    @Override
//    public VirtualAccount saveVirtualAccount(Long userId, String accountName, String accountNumber, String bank, boolean active, String createdAt, String upDatedAt) {
//        VirtualAccount virtualAccount = new VirtualAccount();
//        virtualAccount.setUserId(userId);
//        virtualAccount.setAccountName(accountName);
//        virtualAccount.setAccountNumber(accountNumber);
//        virtualAccount.setBank(bank);
//        virtualAccount.setActive(active);
//        virtualAccount.setCreatedAt(createdAt);
//        virtualAccount.setUpDatedAt(upDatedAt);
//        return virtualAccountRepository.save(virtualAccount);
//    }