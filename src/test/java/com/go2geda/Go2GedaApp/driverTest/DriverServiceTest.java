package com.go2geda.Go2GedaApp.driverTest;

import com.go2geda.Go2GedaApp.data.models.Driver;
import com.go2geda.Go2GedaApp.dtos.request.*;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.Go2gedaBaseException;
import com.go2geda.Go2GedaApp.exceptions.UserNotFound;
import com.go2geda.Go2GedaApp.services.DriverService;
import com.go2geda.Go2GedaApp.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest @Slf4j
@ActiveProfiles("dev")
public class DriverServiceTest {
    private final DriverService driverService;
    private final LoginService loginService;

    @Autowired
    public DriverServiceTest(DriverService driverService, LoginService loginService) {
        this.driverService = driverService;
        this.loginService = loginService;
    }

    @Test
    void registerDriver(){
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("registerDriver@gmail.com");
        firstDriverUser.setFirstName("Oluchi");
        firstDriverUser.setLastName("Duru");
        firstDriverUser.setPhoneNumber("08119863971");
        firstDriverUser.setPassword("registerDriver");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);

        log.info(firstDriver.toString());

//        assertThat(firstDriver).isNotNull();
    }

    @Test
    void driverCannotRegisterWithDuplicateEmail(){
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("oluchi@gmail.com");
        firstDriverUser.setFirstName("Oluchi");
        firstDriverUser.setLastName("Duru");
        firstDriverUser.setPhoneNumber("08119863971");
        firstDriverUser.setPassword("DuruOluchi");

        assertThrows(Go2gedaBaseException.class, ()->driverService.register(firstDriverUser));

        assertThat(driverService.emailExist("oluchi@gmail.com")).isTrue();


    }



    @Test
    void driverCanLoginUser(){
        DriverRegisterUserRequest firstDriver = new DriverRegisterUserRequest();
        firstDriver.setEmail("driverlogin2@gmail.com");
        firstDriver.setFirstName("Dey");
        firstDriver.setLastName("Play");
        firstDriver.setPhoneNumber("90787878");
        firstDriver.setPassword("deyplaypassword");

        RegisterUserResponse firstCommuter = driverService.register(firstDriver);

        assertThat(firstCommuter).isNotNull();


        LoginRequest request = new LoginRequest();
        request.setEmail("driverlogin2@gmail.com");
        request.setPassword("deyplaypassword");

        RegisterUserResponse response = loginService.login(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();

    }

    @Test
    void loginWithWrongCredentials(){
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("deyplay3@gmail.com");
        firstDriverUser.setFirstName("Dey");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstCommuter = driverService.register(firstDriverUser);

        assertThat(firstCommuter).isNotNull();

        LoginRequest request = new LoginRequest();
        request.setEmail("deyplay3@gmail.com");
        request.setPassword("deyplaypassworded");

//        assertThrows(UserNotFound.class, ()->userService.login(request));
//
//        assertThatThrownBy(()->userService.login(request)).isInstanceOf(UserNotFound.class).hasMessage(USER_NOT_FOUND.name());
    }


    @Test
    void verifyDriverAccountDetails(){

        DriverRegisterUserRequest driverRegisterUserRequest = new DriverRegisterUserRequest();
        driverRegisterUserRequest.setEmail("verifyDriverAccountdetails@gmail.com");
        driverRegisterUserRequest.setFirstName("Dey");
        driverRegisterUserRequest.setLastName("Play");
        driverRegisterUserRequest.setPhoneNumber("90787878");
        driverRegisterUserRequest.setPassword("deyplaypassword");

        driverService.register(driverRegisterUserRequest);

        String bankVerificationNUmber = "1212121212";
        String accountNUmber = "1234567890";
        String bankName = "Go2Geda Bank PLC.";

        AccountDetailsVerificationRequest verificationRequest = new AccountDetailsVerificationRequest();
        verificationRequest.setAccountNUmber(accountNUmber);
        verificationRequest.setBankName(bankName);
        verificationRequest.setBankVerificationNUmber(bankVerificationNUmber);

        Driver foundDriver = null;
        try {
            foundDriver = driverService.findDriverByEmail("verifyDriverAccountdetails@gmail.com");
        } catch (UserNotFound e) {
            throw new RuntimeException(e);
        }
        log.info(foundDriver.toString());

        String email = foundDriver.getUser().getBasicInformation().getEmail();

        OkResponse response = null;
        try {
            response = driverService.verifyDriverAccountDetails(verificationRequest, email);
        } catch (UserNotFound e) {
            throw new RuntimeException(e);
        }

        assertThat(response).isNotNull();
    }

    @Test
    void verifyDriverAddress(){
        DriverRegisterUserRequest registerUserRequest = new DriverRegisterUserRequest();
        registerUserRequest.setEmail("dejalltime22@gmail.com");
        registerUserRequest.setFirstName("Dej");
        registerUserRequest.setLastName("Martins");
        registerUserRequest.setPhoneNumber("90787878");
        registerUserRequest.setPassword("deyplaypassword");

        driverService.register(registerUserRequest);

        String address = "13 ST. Jones";
        String localGovernment = "Yaba";
        String state = "Lagos";

        AddressVerificationRequest request = new AddressVerificationRequest();
        request.setHomeAddress(address);
        request.setState(state);
        request.setLocalGovernment(localGovernment);

        OkResponse response = null;
        try {
            response = driverService.verifyAddress(request,  "dejalltime22@gmail.com");
        } catch (UserNotFound e) {
            throw new RuntimeException(e);
        }

        assertThat(response).isNotNull();
    }

    @Test
    void verifyDriverLicenseTest(){
//        String frontPicture = appConfig.getDriverLicenseFrontPictureTest();
//
//        String backPicture = appConfig.getDriverLicenseBackPictureTest();

        Path pathOne = Paths.get("C:\\Users\\SHERIF\\IdeaProjects\\GO2GEDA-BACKEND\\GO2GEDA\\src\\main\\resources\\static\\assets\\drivers-license-back.jpg");
        Path pathTwo = Paths.get("C:\\Users\\SHERIF\\IdeaProjects\\NewGo2gedaBackend\\src\\main\\resources\\static\\assests\\frontlicense.jpeg");

        try {
            InputStream inputStream = Files.newInputStream(pathOne);
            InputStream inputStream2 = Files.newInputStream(pathTwo);

            MultipartFile multipartFrontFile = new MockMultipartFile("test",inputStream);
            MultipartFile multipartBackFile = new MockMultipartFile("test",inputStream2);

            DriverLicenceVerificationRequest verificationRequest = new DriverLicenceVerificationRequest();
            verificationRequest.setFrontPicture(multipartFrontFile);
            verificationRequest.setBackPicture(multipartBackFile);

            OkResponse uploadedVideo = driverService.verifyDriverLicense(verificationRequest);
            assertThat(uploadedVideo).isNotNull();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }





    }
}
