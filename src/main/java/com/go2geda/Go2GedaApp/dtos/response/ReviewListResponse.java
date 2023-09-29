package com.go2geda.Go2GedaApp.dtos.response;

import com.go2geda.Go2GedaApp.data.models.Review;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewListResponse extends BaseResponse{
    List<Review> reviewList;


    public ReviewListResponse(boolean isSuccessful, int responseCode, String message, LocalDateTime timeStamp,List<Review> reviewList) {
        super(isSuccessful, responseCode, message, timeStamp);


        this.reviewList = reviewList;
    }
}
