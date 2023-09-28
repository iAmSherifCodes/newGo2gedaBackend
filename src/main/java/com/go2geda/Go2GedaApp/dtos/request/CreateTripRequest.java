package com.go2geda.Go2GedaApp.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CreateTripRequest {
    private String from;
    private String to;
    private Integer pricePerSeat;
    private Integer numberOfSeats;
    private LocalDateTime pickUpTime;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private String TripStatus;
    private String email;
}
