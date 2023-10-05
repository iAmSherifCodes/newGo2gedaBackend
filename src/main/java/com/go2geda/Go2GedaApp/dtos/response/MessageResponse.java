package com.go2geda.Go2GedaApp.dtos.response;

import com.go2geda.Go2GedaApp.data.models.User;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageResponse {
    private  String firstName;
    private String lastName;
    private String chatMessage;
}
