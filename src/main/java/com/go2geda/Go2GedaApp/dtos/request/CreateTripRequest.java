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
    private Long driverId;
}
