package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PaystackLogResponse {
    private String start_time;
    private String time_spent;
    private String attempts;
    private Integer errors;
    private Boolean success;
    private Boolean mobile;
    //    private Arrays input;

    private String authentication;
    private String channel;
    private List<PaystackHistoryResponse> history;
}
