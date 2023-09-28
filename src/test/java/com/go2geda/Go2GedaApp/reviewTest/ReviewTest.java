//package com.go2geda.Go2GedaApp.reviewTest;
//
//import com.go2geda.Go2GedaApp.data.models.Review;
//import com.go2geda.Go2GedaApp.data.models.User;
//import com.go2geda.Go2GedaApp.repositories.ReviewRepository;
//import com.go2geda.Go2GedaApp.repositories.UserRepository;
//import com.go2geda.Go2GedaApp.services.ReviewService;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//public class ReviewTest {
//
//
//        private final ReviewService reviewService;
//        private final ReviewRepository reviewRepository;
//        private final UserRepository userRepository;
//
//
//
//        @BeforeEach
//        public void init() {
//
//        }
//        @Test
//        void testToCreateReview(){
//                User driver = new User();
//                driver.setId(1L);
//                driver.setVerified(true);
//                userRepository.save(driver);
//
//                Review review = new Review();
//                review.setReview("you no sabi drive");
//                review.setUser(driver);
//                reviewRepository.save(review);
//            List<Review> driversReview = reviewService.getReviewOfAUsers(driver.getId());
//            assertEquals(driversReview.size(),1);
//        }
//        @Test
//        void testToGetReviewOfAUser(){
//                User driver = new User();
//                driver.setId(2L);
//                driver.setVerified(true);
//                userRepository.save(driver);
//
//                Review review = new Review();
//                review.setReview("you no sabi drive");
//                review.setUser(driver);
//                reviewRepository.save(review);
//
//                Review review2 = new Review();
//                review2.setReview("you no sabi drive");
//                review2.setUser(driver);
//                reviewRepository.save(review2);
//                List<Review> driversReview = reviewService.getReviewOfAUsers(driver.getId());
//                assertEquals(driversReview.size(),2);
//
//        }
//}
