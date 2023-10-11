package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VirtualAccountAssignmentResponse {
    private Long integration;
    private String assignee_id;
    private String assignee_type;
    private boolean expired;
    private String account_type;
    private String assigned_at;

}
