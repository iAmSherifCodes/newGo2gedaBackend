
package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.Review;
import com.go2geda.Go2GedaApp.data.models.User;
import com.go2geda.Go2GedaApp.dtos.request.ReviewRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.exceptions.UserDoesNotExist;
import com.go2geda.Go2GedaApp.exceptions.UserNotFound;
import com.go2geda.Go2GedaApp.repositories.ReviewRepository;
import com.go2geda.Go2GedaApp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class Go2GedaReviewService implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
@Override
public Review createReview(ReviewRequest reviewRequest) {
    Optional<User> sender = userRepository.findById(reviewRequest.getSenderId());
    Optional<User> receiver = userRepository.findById(reviewRequest.getReceiverId());

    if (sender.isEmpty() || receiver.isEmpty()) {
        // One or both of the users don't exist
        throw new UserDoesNotExist( "User Cannot be found");
    } else {
        Review review = new Review();
        review.setReview(reviewRequest.getReview());
        review.setSenderId(reviewRequest.getSenderId());
        review.setReceiverId(reviewRequest.getReceiverId());
        Review savedReview = reviewRepository.save(review);
        receiver.get().getReviews().add(savedReview);
        userRepository.save(receiver.get());
        return reviewRepository.save(review);
    }
}
    @Override
    public List<Review> getReviewOfAUsers(Long receiverId) {
       Optional<User>reviewer = userRepository.findById(receiverId);
       return reviewer.get().getReviews();
    }
    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).get();
    }
    }

