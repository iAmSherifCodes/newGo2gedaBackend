package com.go2geda.Go2GedaApp.services;

import com.cloudinary.api.exceptions.AlreadyExists;
import com.go2geda.Go2GedaApp.data.models.VirtualAccount;
import com.go2geda.Go2GedaApp.dtos.request.CreateVirtualAccountRequest;
import com.go2geda.Go2GedaApp.dtos.response.VirtualAccountResponse;

public interface VirtualAccountService {
    VirtualAccountResponse createVirtualAccount(CreateVirtualAccountRequest createVirtualAccountRequest, Long userId, String walletPin) throws AlreadyExists;

    VirtualAccount saveVirtualAccount(Long userId, String accountName, String accountNumber,  boolean active, String createdAt, String upDatedAt);
}
