package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingNotificationResponse {
    private String firstName;
    private String lastName;
    private String url;
    private Long tripId;
}
