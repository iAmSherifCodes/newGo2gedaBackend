package com.go2geda.Go2GedaApp.tripServiceTest;

import com.go2geda.Go2GedaApp.data.models.TripStatus;
import com.go2geda.Go2GedaApp.dtos.request.AcceptAndRejectRequest;
import com.go2geda.Go2GedaApp.dtos.request.CreateTripRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;
import com.go2geda.Go2GedaApp.services.DriverService;
import com.go2geda.Go2GedaApp.services.TripService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class TripServiceTest {
    private final TripService tripService;
    private final DriverService driverService;

    @Autowired
    public TripServiceTest(TripService tripService, DriverService driverService) {
        this.tripService = tripService;
        this.driverService = driverService;

    }

    @Test
    public void testThatDriverCanCreateTrip() throws NotFoundException {
//        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
//        firstDriverUser.setEmail("obinaligoodness@gmail.com");
//        firstDriverUser.setFirstName("Sherif");
//        firstDriverUser.setLastName("Play");
//        firstDriverUser.setPhoneNumber("90787878");
//        firstDriverUser.setPassword("deyplaypassword");

//        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
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
        var canceledTrip = tripService.cancelTrip(1L);
        var foundTrip = tripService.viewTrip(1L);
        assertThat(foundTrip.getTripStatus().equals(TripStatus.CANCELED));
        System.out.println(foundTrip.getTripStatus());
    }

    @Test
    public void testThatDriverCanStartTrip() throws NotFoundException {
        var startedTrip = tripService.startTrip(1L);
        var foundTrip = tripService.viewTrip(1L);
        assertThat(foundTrip.getTripStatus()==TripStatus.STARTED);
    }
    @Test
    public void testThatCommuterCanSearchTripByFrom() throws NotFoundException {
        var searchedTrip = tripService.searchTripByFrom("Abulegba");
        assertThat(searchedTrip).isNotNull();
    }
    @Test
    public void testThatCommuterCanSearchTripByTo() throws NotFoundException {
        var searchedTrip = tripService.searchTripByTo("Ojuelegba");
        assertThat(searchedTrip).isNotNull();
    }
    @Test
    public void testThatDriverCanEndTrip() throws NotFoundException {
        var startedTrip = tripService.endTrip(1L);
        var foundTrip = tripService.viewTrip(1L);
        assertThat(foundTrip.getTripStatus()==TripStatus.STARTED);
    }
    @Test
    public void testThatCommuterCanBookTrip() throws NotFoundException {
        tripService.bookTrip(1L,2L);
        var foundTrip = tripService.viewTrip(2L);
        assertThat(foundTrip.getDriver().getNotificationList()).isNotNull();
        System.out.println(foundTrip.getDriver().getNotificationList());
    }
    @Test
    public void testThatDriverCanAcceptBookRequest() throws NotFoundException {
        AcceptAndRejectRequest acceptAndRejectRequest = new AcceptAndRejectRequest();
        acceptAndRejectRequest.setTripId(2L);
        acceptAndRejectRequest.setCommuterId(152L);
        tripService.acceptTripRequest(acceptAndRejectRequest);
        var foundTrip = tripService.viewTrip(2L);
        assertThat(foundTrip.getCommuter()).isNotNull();
    }

    @Test
    public void testThatDriverCanRejectBookingRequest() throws NotFoundException {
        AcceptAndRejectRequest acceptAndRejectRequest = new AcceptAndRejectRequest();
        acceptAndRejectRequest.setTripId(2L);
        acceptAndRejectRequest.setCommuterId(52L);
        tripService.rejectTripRequest(acceptAndRejectRequest);
        var foundTrip = tripService.viewTrip(2L);
        assertThat(foundTrip.getCommuter().size()==1);
    }
    @Test
    public void testThatCommuterCanViewTheirBookedTrips() throws NotFoundException {
       var bookedTrip =  tripService.viewCommuterBookedTrips(152L);
        System.out.println(bookedTrip.get(0));
       assertEquals(1,bookedTrip.size());
    }
}
