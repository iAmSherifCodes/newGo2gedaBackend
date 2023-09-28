package com.go2geda.Go2GedaApp.dtos.request;

import com.go2geda.Go2GedaApp.data.models.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewRequest {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String review;
}
