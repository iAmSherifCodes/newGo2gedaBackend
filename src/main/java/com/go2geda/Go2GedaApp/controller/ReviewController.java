package com.go2geda.Go2GedaApp.controller;

import com.go2geda.Go2GedaApp.data.models.Review;
import com.go2geda.Go2GedaApp.dtos.request.ReviewRequest;
import com.go2geda.Go2GedaApp.dtos.response.BaseResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterReviewResponse;
import com.go2geda.Go2GedaApp.dtos.response.ReviewListResponse;
import com.go2geda.Go2GedaApp.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService review;


    @PostMapping("/register")
    public ResponseEntity<BaseResponse> createReview(@RequestBody ReviewRequest reviewRequest){
        Review response = review.createReview(reviewRequest);
        return new ResponseEntity<>(
                new RegisterReviewResponse(true, HttpStatus.CREATED.value(), "successful",
                        LocalDateTime.now(), response)
                ,HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<BaseResponse> findReviewForUser(@PathVariable  Long id){
            List<Review> reviews = review.getReviewOfAUsers(id);
            return new ResponseEntity<>(new ReviewListResponse(true,HttpStatus.FOUND.value(),
                    "succesful",LocalDateTime.now(),reviews),HttpStatus.FOUND);
        }
    }

