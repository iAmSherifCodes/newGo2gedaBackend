package com.go2geda.Go2GedaApp.tripServiceTest;

import com.go2geda.Go2GedaApp.data.models.TripStatus;
import com.go2geda.Go2GedaApp.dtos.request.AcceptAndRejectRequest;
import com.go2geda.Go2GedaApp.dtos.request.CommuterRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.request.CreateTripRequest;
import com.go2geda.Go2GedaApp.dtos.request.DriverRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;
import com.go2geda.Go2GedaApp.services.CommuterService;
import com.go2geda.Go2GedaApp.services.DriverService;
import com.go2geda.Go2GedaApp.services.TripService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@ActiveProfiles("dev")
@Slf4j
public class TripServiceTest {
    private final TripService tripService;
    private final DriverService driverService;
    private final CommuterService commuterService;

    @Autowired
    public TripServiceTest(TripService tripService, DriverService driverService,CommuterService commuterService) {
        this.tripService = tripService;
        this.driverService = driverService;
        this.commuterService = commuterService;

    }

    @Test
    public void testThatDriverCanCreateTrip() throws NotFoundException {
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("obinaligoodness@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        CreateTripRequest createTripRequest = new CreateTripRequest();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        createTripRequest.setFrom("Abulegba");
        createTripRequest.setTo("Ojuelegba");
        createTripRequest.setPricePerSeat(1000);
        createTripRequest.setNumberOfSeats(3);
        createTripRequest.setPickUpTime(localDateTime);
        createTripRequest.setEndTime(endTime);
        createTripRequest.setStartTime(endTime);
        createTripRequest.setTripStatus("CREATED");
        createTripRequest.setEmail("obinaligoodness@gmail.com");
        OkResponse response = tripService.createTrip(createTripRequest);
        assertThat(response).isNotNull();

    }
    @Test
    public void testThatDriverCanCancelTrip() throws NotFoundException {
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("obinaligoodness@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        CreateTripRequest createTripRequest = new CreateTripRequest();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        createTripRequest.setFrom("Abulegba");
        createTripRequest.setTo("Ojuelegba");
        createTripRequest.setPricePerSeat(1000);
        createTripRequest.setNumberOfSeats(3);
        createTripRequest.setPickUpTime(localDateTime);
        createTripRequest.setEndTime(endTime);
        createTripRequest.setStartTime(endTime);
        createTripRequest.setTripStatus("CREATED");
        createTripRequest.setEmail("obinaligoodness@gmail.com");
        OkResponse response = tripService.createTrip(createTripRequest);
        var canceledTrip = tripService.cancelTrip(response.getId());
        var foundTrip = tripService.viewTrip(response.getId());
        assertThat(foundTrip.getTripStatus().equals(TripStatus.CANCELED));
        System.out.println(foundTrip.getTripStatus());
    }

    @Test
    public void testThatDriverCanStartTrip() throws NotFoundException {
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("obinaligoodness@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        CreateTripRequest createTripRequest = new CreateTripRequest();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        createTripRequest.setFrom("Abulegba");
        createTripRequest.setTo("Ojuelegba");
        createTripRequest.setPricePerSeat(1000);
        createTripRequest.setNumberOfSeats(3);
        createTripRequest.setPickUpTime(localDateTime);
        createTripRequest.setEndTime(endTime);
        createTripRequest.setStartTime(endTime);
        createTripRequest.setTripStatus(TripStatus.CREATED.toString());
        createTripRequest.setEmail("obinaligoodness@gmail.com");
        OkResponse response = tripService.createTrip(createTripRequest);

        var startedTrip = tripService.startTrip(response.getId());
        var foundTrip = tripService.viewTrip(response.getId());
        assertThat(foundTrip.getTripStatus()==TripStatus.STARTED);
    }
    @Test
    public void testThatCommuterCanSearchTripByFrom() throws NotFoundException {
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("obinaligoodness@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        CreateTripRequest createTripRequest = new CreateTripRequest();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        createTripRequest.setFrom("Abulegba");
        createTripRequest.setTo("Ojuelegba");
        createTripRequest.setPricePerSeat(1000);
        createTripRequest.setNumberOfSeats(3);
        createTripRequest.setPickUpTime(localDateTime);
        createTripRequest.setEndTime(endTime);
        createTripRequest.setStartTime(endTime);
        createTripRequest.setTripStatus(TripStatus.CREATED.toString());
        createTripRequest.setEmail("obinaligoodness@gmail.com");
        OkResponse response = tripService.createTrip(createTripRequest);
        var searchedTrip = tripService.searchTripByFrom("Abulegba");
        assertThat(searchedTrip).isNotNull();
    }
    @Test
    public void testThatCommuterCanSearchTripByTo() throws NotFoundException {
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("obinaligoodness@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        CreateTripRequest createTripRequest = new CreateTripRequest();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        createTripRequest.setFrom("Abulegba");
        createTripRequest.setTo("Ojuelegba");
        createTripRequest.setPricePerSeat(1000);
        createTripRequest.setNumberOfSeats(3);
        createTripRequest.setPickUpTime(localDateTime);
        createTripRequest.setEndTime(endTime);
        createTripRequest.setStartTime(endTime);
        createTripRequest.setTripStatus(TripStatus.CREATED.toString());
        createTripRequest.setEmail("obinaligoodness@gmail.com");
        OkResponse response = tripService.createTrip(createTripRequest);
        var searchedTrip = tripService.searchTripByTo("Ojuelegba");
        assertThat(searchedTrip).isNotNull();
    }
    @Test
    public void testThatDriverCanEndTrip() throws NotFoundException {
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("obinaligoodness@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        CreateTripRequest createTripRequest = new CreateTripRequest();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        createTripRequest.setFrom("Abulegba");
        createTripRequest.setTo("Ojuelegba");
        createTripRequest.setPricePerSeat(1000);
        createTripRequest.setNumberOfSeats(3);
        createTripRequest.setPickUpTime(localDateTime);
        createTripRequest.setEndTime(endTime);
        createTripRequest.setStartTime(endTime);
        createTripRequest.setTripStatus(TripStatus.CREATED.toString());
        createTripRequest.setEmail("obinaligoodness@gmail.com");
        OkResponse response = tripService.createTrip(createTripRequest);
        var startedTrip = tripService.endTrip(response.getId());
        var foundTrip = tripService.viewTrip(response.getId());
        assertThat(foundTrip.getTripStatus()==TripStatus.STARTED);
    }
    @Test
    public void testThatCommuterCanBookTrip() throws NotFoundException {
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("obinaligoodness@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        CreateTripRequest createTripRequest = new CreateTripRequest();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        createTripRequest.setFrom("Abulegba");
        createTripRequest.setTo("Ojuelegba");
        createTripRequest.setPricePerSeat(1000);
        createTripRequest.setNumberOfSeats(3);
        createTripRequest.setPickUpTime(localDateTime);
        createTripRequest.setEndTime(endTime);
        createTripRequest.setStartTime(endTime);
        createTripRequest.setTripStatus(TripStatus.CREATED.toString());
        createTripRequest.setEmail("obinaligoodness@gmail.com");
        OkResponse response = tripService.createTrip(createTripRequest);

        CommuterRegisterUserRequest firstCommuterUser = new CommuterRegisterUserRequest();
        firstCommuterUser.setEmail("woman@gmail.com");
        firstCommuterUser.setFirstName("woman");
        firstCommuterUser.setLastName("Playplay");
        firstCommuterUser.setPhoneNumber("90787878");
        firstCommuterUser.setPassword("deyplaypassword");

        RegisterUserResponse commuter = commuterService.register(firstCommuterUser);

        AcceptAndRejectRequest acceptAndRejectRequest = new AcceptAndRejectRequest();
        acceptAndRejectRequest.setTripId(response.getId());
        acceptAndRejectRequest.setCommuterId(commuter.getId());
        tripService.bookTrip(acceptAndRejectRequest);
        var foundTrip = tripService.viewTrip(response.getId());
        assertThat(foundTrip.getDriver().getNotificationList()).isNotNull();
        System.out.println(foundTrip.getDriver().getNotificationList());
    }
    @Test
    public void testThatDriverCanAcceptBookRequest() throws NotFoundException {
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("obinaligoodness@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        CreateTripRequest createTripRequest = new CreateTripRequest();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        createTripRequest.setFrom("Abulegba");
        createTripRequest.setTo("Ojuelegba");
        createTripRequest.setPricePerSeat(1000);
        createTripRequest.setNumberOfSeats(3);
        createTripRequest.setPickUpTime(localDateTime);
        createTripRequest.setEndTime(endTime);
        createTripRequest.setStartTime(endTime);
        createTripRequest.setTripStatus(TripStatus.CREATED.toString());
        createTripRequest.setEmail("obinaligoodness@gmail.com");
        OkResponse response = tripService.createTrip(createTripRequest);

        CommuterRegisterUserRequest firstCommuterUser = new CommuterRegisterUserRequest();
        firstCommuterUser.setEmail("woman@gmail.com");
        firstCommuterUser.setFirstName("woman");
        firstCommuterUser.setLastName("Playplay");
        firstCommuterUser.setPhoneNumber("90787878");
        firstCommuterUser.setPassword("deyplaypassword");

        RegisterUserResponse commuter = commuterService.register(firstCommuterUser);

        AcceptAndRejectRequest acceptAndRejectRequest = new AcceptAndRejectRequest();
        acceptAndRejectRequest.setTripId(response.getId());
        acceptAndRejectRequest.setCommuterId(commuter.getId());
        tripService.acceptTripRequest(acceptAndRejectRequest);
        var foundTrip = tripService.viewTrip(response.getId());
        assertThat(foundTrip.getCommuter()).isNotNull();
    }

    @Test
    public void testThatDriverCanRejectBookingRequest() throws NotFoundException {
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("obinaligoodness@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        CreateTripRequest createTripRequest = new CreateTripRequest();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        createTripRequest.setFrom("Abulegba");
        createTripRequest.setTo("Ojuelegba");
        createTripRequest.setPricePerSeat(1000);
        createTripRequest.setNumberOfSeats(3);
        createTripRequest.setPickUpTime(localDateTime);
        createTripRequest.setEndTime(endTime);
        createTripRequest.setStartTime(endTime);
        createTripRequest.setTripStatus(TripStatus.CREATED.toString());
        createTripRequest.setEmail("obinaligoodness@gmail.com");
        OkResponse response = tripService.createTrip(createTripRequest);

        CommuterRegisterUserRequest firstCommuterUser = new CommuterRegisterUserRequest();
        firstCommuterUser.setEmail("woman@gmail.com");
        firstCommuterUser.setFirstName("woman");
        firstCommuterUser.setLastName("Playplay");
        firstCommuterUser.setPhoneNumber("90787878");
        firstCommuterUser.setPassword("deyplaypassword");

        RegisterUserResponse commuter = commuterService.register(firstCommuterUser);

        AcceptAndRejectRequest acceptAndRejectRequest = new AcceptAndRejectRequest();
        acceptAndRejectRequest.setTripId(response.getId());
        acceptAndRejectRequest.setCommuterId(commuter.getId());
        tripService.rejectTripRequest(acceptAndRejectRequest);
        var foundTrip = tripService.viewTrip(response.getId());
        assertThat(foundTrip.getCommuter().size()==0);
    }
    @Test
    public void testThatCommuterCanViewTheirBookedTrips() throws NotFoundException {

        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("obinaligoodness@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        CreateTripRequest createTripRequest = new CreateTripRequest();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        createTripRequest.setFrom("Abulegba");
        createTripRequest.setTo("Ojuelegba");
        createTripRequest.setPricePerSeat(1000);
        createTripRequest.setNumberOfSeats(3);
        createTripRequest.setPickUpTime(localDateTime);
        createTripRequest.setEndTime(endTime);
        createTripRequest.setStartTime(endTime);
        createTripRequest.setTripStatus(TripStatus.CREATED.toString());
        createTripRequest.setEmail("obinaligoodness@gmail.com");
        OkResponse response = tripService.createTrip(createTripRequest);

        CommuterRegisterUserRequest firstCommuterUser = new CommuterRegisterUserRequest();
        firstCommuterUser.setEmail("woman@gmail.com");
        firstCommuterUser.setFirstName("woman");
        firstCommuterUser.setLastName("Playplay");
        firstCommuterUser.setPhoneNumber("90787878");
        RegisterUserResponse commuter = commuterService.register(firstCommuterUser);

       var bookedTrip =  tripService.viewCommuterBookedTrips(commuter.getId());
        System.out.println(bookedTrip.get(0));
       assertEquals(0,bookedTrip.size());
    }
}
