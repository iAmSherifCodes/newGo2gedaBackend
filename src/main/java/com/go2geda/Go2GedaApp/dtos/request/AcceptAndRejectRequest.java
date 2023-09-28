package com.go2geda.Go2GedaApp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AcceptAndRejectRequest {
    private Long commuterId;
    private Long tripId;
}
