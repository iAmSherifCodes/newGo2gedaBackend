//package com.go2geda.Go2GedaApp.services;
//
//import com.go2geda.Go2GedaApp.data.models.Review;
//import com.go2geda.Go2GedaApp.data.models.User;
//import com.go2geda.Go2GedaApp.dtos.request.ReviewRequest;
//import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
//import com.go2geda.Go2GedaApp.repositories.ReviewRepository;
//import com.go2geda.Go2GedaApp.repositories.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@AllArgsConstructor
//public class Go2GedaReviewService implements ReviewService{
//    private final ReviewRepository reviewRepository;
//    private final UserRepository userRepository;
//    @Override
//    public Review createReview(ReviewRequest reviewRequest) {
//        Optional<User> user = userRepository.findById(reviewRequest.getUserid());
//       Review review = new Review();
//          review.getSenderId(user.get().);
//          review.setReview(reviewRequest.getReview());
//          return reviewRepository.save(review);
//    }
//
//    @Override
//    public List<Review> getReviewOfAUsers(Long userid) {
//        Optional<User> user = userRepository.findById(userid);
//        List<Review>reviews = reviewRepository.findByUser(user.get());
//
//        return reviews;
//
//    }
//
//    @Override
//    public Review getReviewById(Long id) {
//        return reviewRepository.findById(id).get();
//    }
//
//    }
//
