package com.go2geda.Go2GedaApp.admin;

import com.go2geda.Go2GedaApp.data.models.Commuter;
import com.go2geda.Go2GedaApp.data.models.Driver;
import com.go2geda.Go2GedaApp.dtos.request.AdminRegistrationRequest;
import com.go2geda.Go2GedaApp.services.AdminService;
import com.go2geda.Go2GedaApp.services.CommuterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminTest {
    private final AdminService adminService;

    @Autowired
    public AdminTest(AdminService adminService) {
        this.adminService = adminService;
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
        assertEquals(8,allCommuters.size());
    }

    @Test
    public void testThatAdminCanFindAllDrivers(){
        List<Driver> allDrivers = adminService.findAllDriver();
        assertEquals(0,allDrivers.size());
    }
    @Test
    public void testThatAdminCanSuspendCommuterAccount(){
        adminService.suspendCommuterAccount(52L);
        var foundCommuter = adminService.findCommuterById(52L);
        assertEquals(false,foundCommuter.getUser().isActive);
    }
    @Test
    public void testThatAdminCanSuspendDriverAccount(){
        adminService.suspendDriverAccount(52L);
        var foundDriver = adminService.findDriverById(52L);
        assertEquals(false,foundDriver.getUser().isActive);
    }
    @Test
    public void testThatAdminCanFindReviewByCustomerId(){
        var customerReviews = adminService.findReviewByCommuterId(52L);
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
