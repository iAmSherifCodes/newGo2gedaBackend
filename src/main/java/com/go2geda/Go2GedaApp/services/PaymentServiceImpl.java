package com.go2geda.Go2GedaApp.services;

import com.cloudinary.api.exceptions.AlreadyExists;
import com.go2geda.Go2GedaApp.configs.PayStackConfig;
import com.go2geda.Go2GedaApp.data.models.Driver;
import com.go2geda.Go2GedaApp.data.models.PayStackCustomer;
import com.go2geda.Go2GedaApp.data.models.User;
import com.go2geda.Go2GedaApp.data.models.Wallet;
import com.go2geda.Go2GedaApp.dtos.request.CreatePayStackCustomerRequest;
import com.go2geda.Go2GedaApp.dtos.request.PayStackValidationRequest;
import com.go2geda.Go2GedaApp.dtos.response.CreatePayStackCustomerDataResponse;
import com.go2geda.Go2GedaApp.dtos.response.CreatePayStackCustomerResponse;
import com.go2geda.Go2GedaApp.dtos.response.PayStackValidationResponse;
import com.go2geda.Go2GedaApp.dtos.response.VirtualAccountBankResponse;
import com.go2geda.Go2GedaApp.exceptions.BadRequestException;
import com.go2geda.Go2GedaApp.exceptions.ResourceNotFoundEception;
import com.go2geda.Go2GedaApp.exceptions.UserDoesNotExist;
import com.go2geda.Go2GedaApp.repositories.*;
import jakarta.persistence.NonUniqueResultException;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.cloudinary.json.JSONObject;


import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
@Service
@AllArgsConstructor
@Slf4j
public class PaymentServiceImpl  implements PaymentService{
    private final WalletRepository walletRepository;
    private final PayStackCustomerRepository payStackCustomerRepository;

    private final DriverRepository driverRepository;
    private final PayStackConfig payStackConfig;
    final String alphaNumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final UserRepository userRepository;
    private final String url = "https://nubapi.com/api/verify";

    private final String getAllBanksUrl = "https://nubapi.com/bank-json";
    private final String bearerToken = "jGWAc25vSM1lgLzWYECQ3w9DbxUUcGpNTty92gU0d2522e33";

   private final BasicInformationRepository basicInformationRepository;

    @Override
    public Wallet createWallet(Long userId, String accountNumber, String bankCode, String walletbvn, String walletPin,String bank) throws AlreadyExists {
       log.info(String.valueOf(userId));
        Optional<Wallet> walletUser = walletRepository.findWalletByUserId(userId);
        Optional<User> user  = userRepository.findById(userId);
        if (walletUser.isPresent()){
            throw new  AlreadyExists("You got a wallet arealdy");
        }
        else if (walletPin.length() <6 | walletPin.length() > 6){
            throw new BadRequestException("Please Input A Six Digit Number");
        }
        else if (user.isEmpty()){
            throw new  ResourceNotFoundEception("userNotFound");
        }

        JSONObject response=  executeApiRequest(url,accountNumber, bankCode,bearerToken );
        log.info(response.toString());
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setWalletName(response.getString("first_name")+" "+response.getString("last_name") );
        wallet.setWalletBank(response.getString("Bank_name"));
        wallet.setWalletPin(walletPin);
        wallet.setBalance(BigDecimal.valueOf(0.0));
        wallet.setWalletBVN(walletbvn);
        wallet.setPaymentLink(generatePaymentLink(user.get()));
        wallet.setDateCreated(LocalDate.now());
        return  walletRepository.save(wallet);

    }

    @Override
    public PayStackCustomer createPayStackCustomer(CreatePayStackCustomerDataResponse payStackCustomerDataResponse, Long userId) {
       Optional<Driver> driver = driverRepository.findById(userId);
       if (driver.isEmpty()){
           throw new UserDoesNotExist("Driver does Not Exist");
       }else {
           PayStackCustomer payStackCustomer = PayStackCustomer.builder()
                   .createdAt(payStackCustomerDataResponse.getCreatedAt())
                   .customerCode(payStackCustomerDataResponse.getCustomer_code())
                   .domain(payStackCustomerDataResponse.getDomain())
                   .email(payStackCustomerDataResponse.getEmail())
                   .first_name(payStackCustomerDataResponse.getFirst_name())
                   .last_name(payStackCustomerDataResponse.getLast_name())
                   .integration(payStackCustomerDataResponse.getIntegration())
                   .driversId(driver.get().getId())
                   .updatedAt(payStackCustomerDataResponse.getUpdatedAt())
                   .risk_action(payStackCustomerDataResponse.getRisk_action())
                   .phone(payStackCustomerDataResponse.getPhone())
                   .metadata(payStackCustomerDataResponse.getMetadata().getReferrer())
                           .
                   build();

                return  payStackCustomerRepository.save(payStackCustomer);
       }
    }

    @Override
    public String generatePaymentLink(User user) {
        Optional<User>  user1  = userRepository.findById(user.getId());
        if (user1.isEmpty()){
            throw new  ResourceNotFoundEception("flight not found");
        }
        else {
            SecureRandom random = new SecureRandom();
            int randomCodeLength = 6;
            StringBuilder code = new StringBuilder(randomCodeLength);
            for (int i =0; i <randomCodeLength; i++){
                code.append(alphaNumeric.charAt(random.nextInt(alphaNumeric.length())));
            }
            String referralCode = code.toString();
            return user1.get().getBasicInformation().getFirstName().concat(referralCode).concat(user1.get().getBasicInformation().getLastName());
        }
    }
    @Override
    public String getPaymentLink(Long userId) {
        Optional<Wallet> wallet = walletRepository.findById(userId);
        if (wallet.isEmpty()){
            throw new ResourceNotFoundEception("Link does not exist");
        }
        return wallet.get().getPaymentLink();
    }
    @Override
    public Wallet addFundToWallet(Long userId, BigDecimal amount) {
        Optional<Wallet> wallet = walletRepository.findById(userId);
        if (wallet.isPresent()){
            wallet.get().setBalance(wallet.get().getBalance().add((amount)));
            walletRepository.save(wallet.get());
            return wallet.get();
        }
        else {
            throw new  ResourceNotFoundEception("wallet not found");
        }
    }

    @Override
    public Wallet deductFundFromWallet(Long userId, BigDecimal feesCharged) {
        Optional<Wallet> wallet =  getWalletByUserId(userId);
        if (wallet.isPresent()){
            BigDecimal currentBalance = wallet.get().getBalance();
            BigDecimal updatedBalance = wallet.get().getBalance().subtract(feesCharged);
            if(feesCharged.compareTo(currentBalance) >= 0){
                throw new ResourceNotFoundEception("Insufficient funds");
            }
            else{
                wallet.get().setBalance(updatedBalance);
                return   walletRepository.save(wallet.get());
            }
        }
        return null;
    }

    @Override
    public BigDecimal getBalance(Long userId) {
        Optional<Wallet> wallet = getWalletByUserId(userId);
        if (wallet.isEmpty()){
            throw new ResourceNotFoundEception("Wallet not found");
        }
        else{
            return wallet.get().getBalance();
        }
    }

    @Override
    public String CheckBalance(BigDecimal amount, Long userId) {
        BigDecimal currentAmount =  getBalance(userId);
        if (amount.compareTo(currentAmount) >= 0){
            return "Insufficient balance";
        }
        else {
            return "true";
        }
    }

    @Override
    public Optional<Wallet> getWalletByUserId(Long userId)throws NonUniqueResultException {
        Optional<Wallet> wallet;
        try {
            wallet = walletRepository.findWalletByUserId(userId);
            if (wallet.isEmpty()){
                throw new ResourceNotFoundEception("Wallet does not exist");
            }
        }
        catch (NonUniqueResultException e){
            throw new NonUniqueResultException("Contact Support");

        }


        return wallet;
    }
@Override
    public   JSONObject executeApiRequest(String apiUrl, String accountNumber, String bankCode, String bearerToken) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();


            URI uri = new URIBuilder(apiUrl)
                    .addParameter("account_number", accountNumber)
                    .addParameter("bank_code", bankCode)
                    .build();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity responseEntity = httpResponse.getEntity();
            String responseString = EntityUtils.toString(responseEntity);
            return new JSONObject(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JSONObject getAllBankCodes() {
//        try {
//            HttpClient httpClient = HttpClientBuilder.create()
//                    .addParameter()
//                    .addParameter("bank_code")
//                    .build();
//
//            URI uri = new URIBuilder(getAllBanksUrl).build();
//            HttpGet httpGet = new HttpGet(uri);
//            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken);
//
//            HttpResponse httpResponse = httpClient.execute(httpGet);
//            int statusCode = httpResponse.getStatusLine().getStatusCode();
//
//            if (statusCode == 200) {
//                HttpEntity responseEntity = httpResponse.getEntity();
//                String responseString = EntityUtils.toString(responseEntity);
//                return new JSONObject(responseString);
//            } else {
//                System.err.println("HTTP request failed with status code: " + statusCode);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
        return null;
    }
    @Override
    public CreatePayStackCustomerResponse createPayStackCustomerRequestHttpEntity(CreatePayStackCustomerRequest createPayStackCustomerRequest, Long userId) {
         boolean emailExist =   basicInformationRepository.existsByEmail(createPayStackCustomerRequest.getEmail());
         if (emailExist){
             RestTemplate restTemplate1 = new RestTemplate();
             org.springframework.http.HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
             httpHeaders.setContentType(MediaType.APPLICATION_JSON);
             httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
             httpHeaders.set("Authorization","Bearer "+payStackConfig.getSecretKey());
             log.info(payStackConfig.getCreateCustomerUrl());
             log.info(payStackConfig.getSecretKey());
             org.springframework.http. HttpEntity<CreatePayStackCustomerRequest>createEntity =
                     new   org.springframework.http. HttpEntity<>(createPayStackCustomerRequest,httpHeaders);
             log.info(String.valueOf(createPayStackCustomerRequest.getEmail()));
             ResponseEntity<CreatePayStackCustomerResponse>response =
                     restTemplate1.postForEntity(payStackConfig.getCreateCustomerUrl(),createEntity,
                             CreatePayStackCustomerResponse.class);
             if (response.getStatusCode()== HttpStatus.OK){
                 createPayStackCustomer(Objects.requireNonNull(response.getBody()).getData(), userId );
                 return response.getBody();
             }
             else {
                 throw new ResourceNotFoundEception("customer not created");
             }
         }
         else {
             throw new ResourceNotFoundEception("Email does not match");
         }

    }

    @Override
    public PayStackValidationResponse validation(PayStackValidationRequest payStackValidationRequest,
                                               String customer_code) {
        Optional<PayStackCustomer> payStackCustomer = payStackCustomerRepository.findByCustomerCode(customer_code);
        ResponseEntity<PayStackValidationResponse> response = null;
        if (payStackCustomer.isPresent()) {
            RestTemplate restTemplate = new RestTemplate();
            org.springframework.http.HttpHeaders header = new org.springframework.http.HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            header.set("Authorization", "Bearer " + payStackConfig.getSecretKey());

            org.springframework.http.HttpEntity<PayStackValidationRequest> entity =
                    new org.springframework.http.HttpEntity<>(payStackValidationRequest, header);
            response = restTemplate.postForEntity(payStackConfig.getCustomerValidationUrl() + customer_code + "/identification",
                    entity, PayStackValidationResponse.class);
            if (response.getStatusCode() == HttpStatus.ACCEPTED) {
                payStackCustomer.get().setBvn(payStackValidationRequest.getBvn());
                payStackCustomerRepository.save(payStackCustomer.get());
                return response.getBody();
            }
            else{
                throw new ResourceNotFoundEception(Objects.requireNonNull(response.getBody()).getMessage());
            }

        }
        else {
            throw new ResourceNotFoundEception("Customer not found");
        }
//        else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
//            throw new ResourceNotFoundEception(Objects.requireNonNull(response.getBody()).getMessage());
//
//        } else {
//
//
//        }


    }
}



//    @Override
//    public String donateWithCard(DonateWithCardRequest donateWithCardRequest) throws MessagingException, IOException {
//        PayStackSubscriptionResponse response = subscriptionService.verifyReference(donateWithCardRequest.getReferenceNumber(), donateWithCardRequest.getSenderId());
//        if (!response.getData().getStatus().equals("success")) {
//            createTransaction(donateWithCardRequest.getReferenceNumber(), new BigDecimal(String.valueOf(response.getData().getAmount())),
//                    response.getData().getCustomer().getFirst_name() + " "+ response.getData().getCustomer().getLast_name(),
//                    response.getData().getAuthorization().getAccount_name(), response.getData().getGateway_response(), response.getData().getStatus(),
//                    donateWithCardRequest.getSenderId(), response.getData().getAuthorization().getCountry_code(), response.getData().getAuthorization().getAccount_name(),
//                    response.getData().getAuthorization().getBank(), null, response.getData().getAuthorization().getBank(), donateWithCardRequest.getReceiverId(), response.getData().getChannel() , response.getData().getTransaction_date() );
//            subscriptionService.createUserATMCard(response.getData().getAuthorization().getBin(), response.getData().getAuthorization().getLast4(), response.getData().getAuthorization().getExp_month(), response.getData().getAuthorization().getExp_year(), response.getData().getAuthorization().getChannel(), response.getData().getAuthorization().getCard_type(), response.getData().getAuthorization().getBank(), response.getData().getAuthorization().getCountry_code(), response.getData().getAuthorization().getBrand(), response.getData().getAuthorization().getReusable(), response.getData().getAuthorization().getSignature(),
//            response.getData().getAuthorization().getAccount_name(), response.getData().getAuthorization().getAuthorization_code(), donateWithCardRequest.getSenderId() );
//
//            log.info("first print"+ response.getData().getGateway_response() + " " + response.getData().getStatus() );
//            throw new ResourceNotFound(response.getData().getGateway_response());
//        }
//        else{
//            log.info("second print"+ response.getData().getGateway_response() + " " + response.getData().getStatus() );
//            Optional<CivilUser> user = userService.findById(donateWithCardRequest.getReceiverId());
//            Optional<Profile> profile = profileRepository.ndProfileByCivilUser(user.get());
//            createTransaction(donateWithCardRequest.getReferefinceNumber(), new BigDecimal(response.getData().getFees()),
//                    response.getData().getCustomer().getFirst_name() + " "+ response.getData().getCustomer().getLast_name(),
//                    response.getData().getAuthorization().getAccount_name(), response.getData().getGateway_response(), response.getData().getStatus(),
//                    donateWithCardRequest.getSenderId(), response.getData().getAuthorization().getCountry_code(), response.getData().getAuthorization().getAccount_name(), response.getData().getAuthorization().getBank(), null, response.getData().getAuthorization().getBank(), donateWithCardRequest.getReceiverId(), response.getData().getChannel() , response.getData().getTransaction_date() );
//            createUserTransaction(donateWithCardRequest.getReferenceNumber(), donateWithCardRequest.getSenderId(), new BigDecimal(String.valueOf(response.getData().getAmount().divide(BigDecimal.valueOf(100)))), ETransactionType.DEBIT, response.getData().getChannel(), response.getData().getGateway_response(), response.getData().getStatus(), response.getData().getTransaction_date());
//            createUserTransaction(donateWithCardRequest.getReferenceNumber(), donateWithCardRequest.getReceiverId(), new BigDecimal(String.valueOf(response.getData().getAmount().divide(BigDecimal.valueOf(100)))), ETransactionType.CREDIT, response.getData().getChannel(), response.getData().getGateway_response(), response.getData().getStatus(), response.getData().getTransaction_date());
//
//            subscriptionService.createUserATMCard(response.getData().getAuthorization().getBin(), response.getData().getAuthorization().getLast4(), response.getData().getAuthorization().getExp_month(), response.getData().getAuthorization().getExp_year(), response.getData().getAuthorization().getChannel(), response.getData().getAuthorization().getCard_type(), response.getData().getAuthorization().getBank(), response.getData().getAuthorization().getCountry_code(), response.getData().getAuthorization().getBrand(), response.getData().getAuthorization().getReusable(), response.getData().getAuthorization().getSignature(), response.getData().getAuthorization().getAccount_name(), response.getData().getAuthorization().getAuthorization_code(), donateWithCardRequest.getSenderId() );
//
//            walletService.addFundToWallet(donateWithCardRequest.getReceiverId(), new BigDecimal(String.valueOf(response.getData().getAmount().divide(BigDecimal.valueOf(100)) )));
//            emailVerificationService.sendBankAccountCreditMail(user.get(),profile.get().getFirstName(), profile.get().getLastName(), response.getData().getTransaction_date(), "Sucesss", "CARD", response.getData().getAmount().divide(BigDecimal.valueOf(100)));
//            return response.getData().getGateway_response();
//        }
//
//    }
//
//





