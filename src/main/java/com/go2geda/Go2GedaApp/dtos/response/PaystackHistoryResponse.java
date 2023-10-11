package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaystackHistoryResponse {
    private String type;
    private String message;
    private Integer time;
}
