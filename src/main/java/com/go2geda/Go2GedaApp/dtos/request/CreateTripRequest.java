package com.go2geda.Go2GedaApp.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CreateTripRequest {
    private String from;
    private String to;
    private int pricePerSeat;
    private int numberOfSeats;
    private String pickUpTime;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
<<<<<<< HEAD
    private String email;
=======
//    private String email;
>>>>>>> 4afa4ad3405e4e2362f8c2da2ae57df4150c5a4b
    private Long driverId;
}
