package com.go2geda.Go2GedaApp.CommuterTest;

import com.go2geda.Go2GedaApp.dtos.request.AddressVerificationRequest;
import com.go2geda.Go2GedaApp.dtos.request.CommuterRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.services.CommuterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CommuterServiceTest {
    private final CommuterService commuterService;

    @Autowired
    public CommuterServiceTest(CommuterService commuterService) {
        this.commuterService = commuterService;
    }

    @Test
    void testCommuterCanRegister(){
        CommuterRegisterUserRequest firstCommuterUser = new CommuterRegisterUserRequest();
        firstCommuterUser.setEmail("awofiranyesherif56789@gmail.com");
        firstCommuterUser.setFirstName("Sheriffa");
        firstCommuterUser.setLastName("Playplay");
        firstCommuterUser.setPhoneNumber("90787878");
        firstCommuterUser.setPassword("deyplaypassword");

        RegisterUserResponse response = commuterService.register(firstCommuterUser);

        assertThat(response).isNotNull();
    }

    @Test
    void testCommuterVerifyAddress(){
        CommuterRegisterUserRequest userRequest = new CommuterRegisterUserRequest();
        userRequest.setEmail("dejalltime11@gmail.com");
        userRequest.setFirstName("Dej");
        userRequest.setLastName("Martins");
        userRequest.setPhoneNumber("90787878");
        userRequest.setPassword("deyplaypassword");

        commuterService.register(userRequest);

        String address = "13 ST. Jones";
        String localGovernment = "Yaba";
        String state = "Lagos";

        AddressVerificationRequest request = new AddressVerificationRequest();
        request.setHomeAddress(address);
        request.setState(state);
        request.setLocalGovernment(localGovernment);

        OkResponse response = commuterService.verifyAddress(request,  "dejalltime11@gmail.com");

        assertThat(response).isNotNull();
    }
}
