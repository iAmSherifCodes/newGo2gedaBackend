package com.go2geda.Go2GedaApp.services;

import com.cloudinary.api.exceptions.AlreadyExists;
import com.go2geda.Go2GedaApp.data.models.PayStackCustomer;
import com.go2geda.Go2GedaApp.data.models.User;
import com.go2geda.Go2GedaApp.data.models.Wallet;
import com.go2geda.Go2GedaApp.dtos.request.CreatePayStackCustomerRequest;
import com.go2geda.Go2GedaApp.dtos.request.PayStackValidationRequest;
import com.go2geda.Go2GedaApp.dtos.response.*;
import org.cloudinary.json.JSONObject;

import java.math.BigDecimal;
import java.util.Optional;

public interface PaymentService {
    Wallet createWallet(Long userId, String bankCode,String accountNumber, String walletbvn, String walletPin ,String bank) throws AlreadyExists;

    PayStackCustomer createPayStackCustomer(CreatePayStackCustomerDataResponse payStackCustomerDataResponse, Long userId);
    String generatePaymentLink(User user);
    String getPaymentLink(Long userId);
    Wallet addFundToWallet(Long userId, BigDecimal amount);
    Wallet deductFundFromWallet(Long userId, BigDecimal feesCharged);
    BigDecimal getBalance(Long userId);
    Optional<Wallet> getWalletByUserId(Long userId);

    String CheckBalance(BigDecimal amount, Long userId);
    JSONObject executeApiRequest(String apiUrl, String accountNumber, String bankCode, String bearerToken);
    JSONObject getAllBankCodes();

    CreatePayStackCustomerResponse
    createPayStackCustomerRequestHttpEntity(CreatePayStackCustomerRequest
                                                    createPayStackCustomerRequest, Long userId);

    PayStackValidationResponse validation(PayStackValidationRequest payStackValidationRequest, String customerCode);

}
