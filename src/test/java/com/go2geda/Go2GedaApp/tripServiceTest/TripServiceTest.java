package com.go2geda.Go2GedaApp.tripServiceTest;

import com.go2geda.Go2GedaApp.data.models.Trip;
import com.go2geda.Go2GedaApp.data.models.TripStatus;
import com.go2geda.Go2GedaApp.dtos.request.AcceptAndRejectRequest;
import com.go2geda.Go2GedaApp.dtos.request.CommuterRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.request.CreateTripRequest;
import com.go2geda.Go2GedaApp.dtos.request.DriverRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;
import com.go2geda.Go2GedaApp.exceptions.UserNotFound;
import com.go2geda.Go2GedaApp.repositories.TripRepository;
import com.go2geda.Go2GedaApp.services.CommuterService;
import com.go2geda.Go2GedaApp.services.DriverService;
import com.go2geda.Go2GedaApp.services.TripService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ActiveProfiles("dev")
@Slf4j
public class TripServiceTest {
    private final TripService tripService;
    private final DriverService driverService;
    private final TripRepository tripRepository;
    private final CommuterService commuterService;
    CreateTripRequest createTripRequest;
    DriverRegisterUserRequest firstDriverUser;
    CommuterRegisterUserRequest firstCommuterUser;

    @Autowired
    public TripServiceTest(TripService tripService, DriverService driverService, TripRepository tripRepository, CommuterService commuterService) {
        this.tripService = tripService;
        this.driverService = driverService;
        this.tripRepository = tripRepository;
        this.commuterService = commuterService;


    }
    @BeforeEach
    public void setUp() throws NotFoundException {
        firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("obinaligoodnss@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");


        createTripRequest = new CreateTripRequest();
//        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
        createTripRequest.setFrom("Abulegba");
        createTripRequest.setTo("Ojuelegba");
        createTripRequest.setPricePerSeat(1000);
        createTripRequest.setNumberOfSeats(3);
        createTripRequest.setPickUpTime("12/12/2023, 12:12:30 PM");
//        createTripRequest.setEndTime(endTime);
//        createTripRequest.setStartTime(endTime);

        firstCommuterUser = new CommuterRegisterUserRequest();
        firstCommuterUser.setEmail("woman@gmail.com");
        firstCommuterUser.setFirstName("woman");
        firstCommuterUser.setLastName("Playplay");
        firstCommuterUser.setPhoneNumber("90787878");
        firstCommuterUser.setPassword("deyplaypassword");
    }

    @Test
    void testDriverTripHistory() throws NotFoundException, UserNotFound {
        RegisterUserResponse registerUserResponse = driverService.register(firstDriverUser);
        Long driverId = registerUserResponse.getId();

        createTripRequest.setDriverId(driverId);
        OkResponse createTripResponse = tripService.createTrip(createTripRequest);
        assertNotNull(createTripResponse);

        List<Trip> foundTrips = tripService.driverTripHistory(driverId);
        assertThat(foundTrips.size()).isGreaterThan(0);

    }

    @Test
    public void testThatDriverCanCreateTrip() throws NotFoundException {
        OkResponse response = tripService.createTrip(createTripRequest);
        assertThat(response).isNotNull();

    }
    @Test
    public void testThatDriverCanCancelTrip() throws NotFoundException {
        OkResponse response = tripService.createTrip(createTripRequest);
        var canceledTrip = tripService.cancelTrip(response.getId());
        var foundTrip = tripService.viewTrip(response.getId());
        assertThat(foundTrip.getTripStatus().equals(TripStatus.CANCELED));
        System.out.println(foundTrip.getTripStatus());
    }

    @Test
    public void testThatDriverCanStartTrip() throws NotFoundException {
        var response =tripService.createTrip(createTripRequest);
        var startedTrip = tripService.startTrip(response.getId());
        var foundTrip = tripService.viewTrip(response.getId());
        assertThat(foundTrip.getTripStatus()==TripStatus.STARTED);
    }
    @Test
    public void testThatCommuterCanSearchTripByFrom() throws NotFoundException {
        var searchedTrip = tripService.searchTripByFrom("Abulegba");
        assertThat(searchedTrip).isNotNull();
    }
    @Test
    public void testThatCommuterCanSearchTripByTo() throws NotFoundException {
        OkResponse response = tripService.createTrip(createTripRequest);
        var searchedTrip = tripService.searchTripByTo("Ojuelegba");
        assertThat(searchedTrip).isNotNull();
    }
    @Test
    public void testThatDriverCanEndTrip() throws NotFoundException {
        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        OkResponse response = tripService.createTrip(createTripRequest);

        var startedTrip = tripService.endTrip(response.getId());
        var foundTrip = tripService.viewTrip(response.getId());
        assertSame(foundTrip.getTripStatus(), TripStatus.STARTED);
    }
    @Test
    public void testThatCommuterCanBookTrip() throws NotFoundException {
        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        OkResponse response = tripService.createTrip(createTripRequest);
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
        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        OkResponse response = tripService.createTrip(createTripRequest);
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
        OkResponse response = tripService.createTrip(createTripRequest);
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
        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
        OkResponse response = tripService.createTrip(createTripRequest);
        RegisterUserResponse commuter = commuterService.register(firstCommuterUser);

       var bookedTrip =  tripService.viewCommuterBookedTrips(commuter.getId());
        System.out.println(bookedTrip.get(0));
       assertEquals(0,bookedTrip.size());
    }

    @Test
    void testFindByStatusQuery(){
        List<Trip> foundTrips = tripRepository.findByTripStatus();
        System.out.println(foundTrips);

    }
}
