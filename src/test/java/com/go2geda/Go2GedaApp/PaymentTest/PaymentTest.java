package com.go2geda.Go2GedaApp.PaymentTest;

import com.go2geda.Go2GedaApp.data.models.User;
import com.go2geda.Go2GedaApp.dtos.request.DriverRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.services.DriverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.go2geda.Go2GedaApp.data.models.Role.COMMUTER;
import static com.go2geda.Go2GedaApp.data.models.Role.DRIVER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PaymentTest {
    @Autowired
    DriverService driverService;



    @Test
    public void testToRegisterAUser(){

        //        -----------------------Register First Driver-------------
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("duwend46@gmail.com");
        firstDriverUser.setFirstName("Oluchi");
        firstDriverUser.setLastName("Duru");
        firstDriverUser.setPhoneNumber("08119863972");
        firstDriverUser.setPassword("myBaby");
        driverService.register(firstDriverUser);

        assertThat(firstDriverUser).isNotNull();

    }
    @Test
    public void testToCreateWallet(){


    }
}
