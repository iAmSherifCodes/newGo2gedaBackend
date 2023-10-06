package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.Commuter;
import com.go2geda.Go2GedaApp.data.models.Driver;
import com.go2geda.Go2GedaApp.data.models.Review;
import com.go2geda.Go2GedaApp.data.models.Trip;
import com.go2geda.Go2GedaApp.dtos.request.AdminRegistrationRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;

import java.util.List;

public interface AdminService {
    RegisterUserResponse registerAdmin(AdminRegistrationRequest adminRegistrationRequest);
    List<Commuter> findAllCommuter();
    List<Driver> findAllDriver();
    Commuter findCommuterById(Long commuterId);
    Driver findDriverById(Long DriverId);
    OkResponse suspendDriverAccount(Long driverId);
    OkResponse suspendCommuterAccount(Long commuterId);
    List<Review> findReviewByCommuterId(Long commuterId);
    List<Review> findReviewByDriverId(Long driverId);
    List<Review> getAllReviews();
    List<Trip> findAllTrip();

    List<Trip> findAllAvailableTrip();
    List<Trip> getAllTripHistory();

}
