package com.go2geda.Go2GedaApp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchTripRequest {
    private String from;
    private String to;
}
