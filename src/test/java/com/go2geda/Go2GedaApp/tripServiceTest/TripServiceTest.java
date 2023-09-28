package com.go2geda.Go2GedaApp.tripServiceTest;

import com.go2geda.Go2GedaApp.dtos.request.CreateTripRequest;
import com.go2geda.Go2GedaApp.dtos.request.DriverRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;
import com.go2geda.Go2GedaApp.services.DriverService;
import com.go2geda.Go2GedaApp.services.TripService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

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
        DriverRegisterUserRequest firstDriverUser = new DriverRegisterUserRequest();
        firstDriverUser.setEmail("cashgraphicx@gmail.com");
        firstDriverUser.setFirstName("Sherif");
        firstDriverUser.setLastName("Play");
        firstDriverUser.setPhoneNumber("90787878");
        firstDriverUser.setPassword("deyplaypassword");

//        RegisterUserResponse firstDriver = driverService.register(firstDriverUser);
//        CreateTripRequest createTripRequest = new CreateTripRequest();
//        LocalDateTime localDateTime = LocalDateTime.now();
//        LocalDateTime endTime = LocalDateTime.now().plusHours(1);
//        createTripRequest.setFrom("Abulegba");
//        createTripRequest.setTo("Ojuelegba");
//        createTripRequest.setPricePerSeat(1000);
//        createTripRequest.setNumberOfSeats(3);
//        createTripRequest.setPickUpTime(localDateTime);
//        createTripRequest.setEndTime(endTime);
//        createTripRequest.setStartTime(endTime);
//        createTripRequest.setTripStatus("CREATED");
//
//        OkResponse response = tripService.createTrip(createTripRequest,"cashgraphicx@gmail.com");
//        assertThat(response).isNotNull();

    }
}
