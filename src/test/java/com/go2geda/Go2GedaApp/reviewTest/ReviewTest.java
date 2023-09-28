package com.go2geda.Go2GedaApp.reviewTest;

import com.go2geda.Go2GedaApp.data.models.Review;
import com.go2geda.Go2GedaApp.data.models.User;
import com.go2geda.Go2GedaApp.dtos.request.CommuterRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.request.DriverRegisterUserRequest;
import com.go2geda.Go2GedaApp.dtos.request.ReviewRequest;
import com.go2geda.Go2GedaApp.repositories.ReviewRepository;
import com.go2geda.Go2GedaApp.repositories.UserRepository;
import com.go2geda.Go2GedaApp.services.CommuterService;
import com.go2geda.Go2GedaApp.services.DriverService;
import com.go2geda.Go2GedaApp.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.go2geda.Go2GedaApp.data.models.Role.COMMUTER;
import static com.go2geda.Go2GedaApp.data.models.Role.DRIVER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReviewTest {
        @Autowired
        private  ReviewService reviewService;
        @Autowired
        private DriverService driverService;
        private DriverRegisterUserRequest driverRegisterUserRequest;
       @Autowired
        private CommuterService commuterService;
        private CommuterRegisterUserRequest commuterRegisterUserRequest;
        @Autowired
        private UserRepository userRepository;

        @BeforeEach
        void setUp(){
                driverRegisterUserRequest = new DriverRegisterUserRequest();
                driverRegisterUserRequest.setEmail("dominicrotimi@gmail2.com");
                driverRegisterUserRequest.setLastName("duru");

                commuterRegisterUserRequest = new CommuterRegisterUserRequest();
                commuterRegisterUserRequest.setEmail("oluchiduru34@gmail.com");
                commuterRegisterUserRequest.setLastName("goodness");
        }
//
//        @Test
//        public void TestToRegisterDriver(){
//                driverService.register(driverRegisterUserRequest);
//                assertNotNull(driverRegisterUserRequest);
//        }
//        @Test
//        public void TestToRegisterCommuter(){
//                commuterService.register(commuterRegisterUserRequest);
//                assertNotNull(commuterRegisterUserRequest);
//        }
        @Test
        public void testToCreateAReview(){
                ReviewRequest reviewRequest = new ReviewRequest();
                reviewRequest.setReview("YEYE DRIVER");
                reviewRequest.setSenderId(1L);
                reviewRequest.setReceiverId(1L);
                reviewService.createReview(reviewRequest);
                assertEquals("YEYE DRIVER",reviewRequest.getReview());


        }
      @Test
      public void testToGetALLReview(){
//          commuterService.register(commuterRegisterUserRequest);
//
//
//          driverService.register(driverRegisterUserRequest);


          ReviewRequest reviewRequest = new ReviewRequest();
          reviewRequest.setReview("YEYE DRIVER");
//          reviewRequest.setId(2L);
           reviewService.createReview(reviewRequest);
            List<Review>reviewList = reviewService.getReviewOfAUsers(reviewRequest.getReceiverId());
            assertEquals(3,reviewList.size());

      }
      @Test
    void getAllReviewsOfADriver(){
         User user = new User();
         user.setRole(COMMUTER);
         User customer = userRepository.save(user);

          User secondUser = new User();
          user.setRole(DRIVER);
          User driver = userRepository.save(secondUser);

          ReviewRequest reviewRequest = new ReviewRequest();
          reviewRequest.setSenderId(customer.getId());
          reviewRequest.setReceiverId(driver.getId());
          reviewRequest.setReview("Good Driver");
          Review customerReview = reviewService.createReview(reviewRequest);

          assertNotNull(customerReview);

          List<Review> foundReview = reviewService.getReviewOfAUsers(driver.getId());
          assertNotNull(foundReview);
          assertEquals(1,foundReview.size());
      }

}
