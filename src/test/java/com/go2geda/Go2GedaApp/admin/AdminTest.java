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
        List<Commuter> allCommuters = adminService.findAllCommuter();
        assertEquals(2,allCommuters.size());
    }

    @Test
    public void testThatAdminCanFindAllDrivers(){
        List<Driver> allDrivers = adminService.findAllDriver();
        assertEquals(0,allDrivers.size());
    }
    @Test
    public void testThatAdminCanSuspendCommuterAccount(){
        CommuterRegisterUserRequest firstCommuterUser = new CommuterRegisterUserRequest();
        firstCommuterUser.setEmail("woman@gmail.com");
        firstCommuterUser.setFirstName("woman");
        firstCommuterUser.setLastName("Playplay");
        firstCommuterUser.setPhoneNumber("90787878");
        firstCommuterUser.setPassword("deyplaypassword");

        RegisterUserResponse response = commuterService.register(firstCommuterUser);
        adminService.suspendCommuterAccount(response.getId());
        var foundCommuter = adminService.findCommuterById(response.getId());
        assertEquals(false,foundCommuter.getUser().isActive);
    }
    @Test
    public void testThatAdminCanSuspendDriverAccount(){

        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("deyplay3@gmail.com");
        firstDriverUser.setFirstName("Dey");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        var foundDriver = adminService.findDriverById(firstDriver.getId());
        assertEquals(false,foundDriver.getUser().isActive);
    }
    @Test
    public void testThatAdminCanFindReviewByCustomerId(){CommuterRegisterUserRequest firstCommuterUser = new CommuterRegisterUserRequest();
        firstCommuterUser.setEmail("man@gmail.com");
        firstCommuterUser.setFirstName("woman");
        firstCommuterUser.setLastName("Playplay");
        firstCommuterUser.setPhoneNumber("90787878");
        firstCommuterUser.setPassword("deyplaypassword");
        RegisterUserResponse response = commuterService.register(firstCommuterUser);
        var customerReviews = adminService.findReviewByCommuterId(response.getId());
        assertEquals(0,customerReviews.size());
    }
//    @Test
//    public void testThatAdminCanFindReviewByDriverId(){
//        var driverReviews = adminService.findReviewByDriverId();
//        assertEquals(0,driverReviews.size());
//    }
    @Test
    public void testThatAdminCanFindAllReviews(){
        var allReviews = adminService.getAllReviews();
        assertEquals(0,allReviews.size());
    }
}
