package com.go2geda.Go2GedaApp.admin;

import com.go2geda.Go2GedaApp.data.models.Commuter;
import com.go2geda.Go2GedaApp.data.models.Driver;
import com.go2geda.Go2GedaApp.dtos.request.AdminRegistrationRequest;
import com.go2geda.Go2GedaApp.dtos.request.CommuterRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.request.DriverRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.services.AdminService;
import com.go2geda.Go2GedaApp.services.CommuterService;
import com.go2geda.Go2GedaApp.services.Go2gedaDriverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("dev")
public class AdminTest {
    private final AdminService adminService;
    private final CommuterService commuterService;
    private final Go2gedaDriverService driverService;

    @Autowired
    public AdminTest(AdminService adminService, CommuterService commuterService, Go2gedaDriverService  driverService) {
        this.adminService = adminService;
        this.commuterService = commuterService;
        this.driverService = driverService;
    }

    public RegisterUserResponse registerADriverWithEmail(String email){
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail(email+"@gmail.com");
        firstDriverUser.setFirstName("Oluchi");
        firstDriverUser.setLastName("Duru");
        firstDriverUser.setPhoneNumber("08119863971");
        firstDriverUser.setPassword("DuruOluchi");

        RegisterUserResponse response = driverService.register(firstDriverUser);
        return response;
    }

    public RegisterUserResponse registerACommuterWithEmail(String email){
        CommuterRegisterUserRequest firstCommuterUser = new CommuterRegisterUserRequest();
        firstCommuterUser.setEmail(email+"@gmail.com");
        firstCommuterUser.setFirstName("woman");
        firstCommuterUser.setLastName("Playplay");
        firstCommuterUser.setPhoneNumber("90787878");
        firstCommuterUser.setPassword("deyplaypassword");

        RegisterUserResponse response = commuterService.register(firstCommuterUser);
        return response;
    }
    @Test
    public void testThatAdminCanRegister(){
        AdminRegistrationRequest adminRegistrationRequest = new AdminRegistrationRequest();
        adminRegistrationRequest.setFirstName("Goodness");
        adminRegistrationRequest.setLastName("Obinali");
        adminRegistrationRequest.setEmail("obinaligoodness@gmail.com");
        adminRegistrationRequest.setPhoneNumber("08133571570");
        adminRegistrationRequest.setPassword("Godisgreat");
        var registeredAdmin = adminService.registerAdmin(adminRegistrationRequest);
        assertEquals("Obinali",registeredAdmin.getLastName());
    }

    @Test
    public void testThatAdminCanFindAllCommuters(){
        registerACommuterWithEmail("testThatAdminCanFindAllCommters");
        registerACommuterWithEmail("testThatAdminCanFindAllCommters1");
        List<Commuter> allCommuters = adminService.findAllCommuter();
        assertThat(allCommuters.size()).isGreaterThan(1);
    }

    @Test
    public void testThatAdminCanFindAllDrivers(){
        registerADriverWithEmail("testTmllDriver");
        registerADriverWithEmail("tindAllDrivers1");
        List<Driver> allDrivers = adminService.findAllDriver();
        assertThat(allDrivers.size()).isGreaterThan(1);
    }

    @Test
    public void testThatAdminCanSuspendCommuterAccount(){

        RegisterUserResponse response = registerACommuterWithEmail("testThatAdminCanSuspendCommuterAccount");
        adminService.suspendCommuterAccount(response.getId());
        var foundCommuter = adminService.findCommuterById(response.getId());
        assertEquals(false,foundCommuter.getUser().isActive);
    }

    @Test
    public void testThatAdminCanSuspendDriverAccount(){

        RegisterUserResponse firstDriver = registerADriverWithEmail("testThatAdminCanSuspendDriverAccount");
        var foundDriver = adminService.findDriverById(firstDriver.getId());
        assertEquals(false,foundDriver.getUser().isActive);
    }
    @Test
    public void testThatAdminCanFindReviewByCustomerId(){CommuterRegisterUserRequest firstCommuterUser = new CommuterRegisterUserRequest();
        RegisterUserResponse response = registerACommuterWithEmail("testThatAdminCanFindReviewByCustomerId");
        var customerReviews = adminService.findReviewByCommuterId(response.getId());
        assertEquals(0,customerReviews.size());
    }

    @Test
    public void testThatAdminCanFindAllReviews(){
        var allReviews = adminService.getAllReviews();
        assertEquals(0,allReviews.size());
    }
}
