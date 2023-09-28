package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.Review;
import com.go2geda.Go2GedaApp.dtos.request.ReviewRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReviewService {
    Review createReview(ReviewRequest reviewRequest);
    List<Review> getReviewOfAUsers(Long userId);
    Review getReviewById(Long id);







}
