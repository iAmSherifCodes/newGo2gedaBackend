package com.go2geda.Go2GedaApp.dtos.response;

import com.go2geda.Go2GedaApp.data.models.Review;

import java.time.LocalDateTime;

public class RegisterReviewResponse extends BaseResponse{
    private Review review;


    public RegisterReviewResponse(boolean isSuccessful, int responseCode, String message, LocalDateTime timeStamp,
                                  Review review) {
        super(isSuccessful, responseCode, message, timeStamp);
      this.review =  review ;

    }
}
