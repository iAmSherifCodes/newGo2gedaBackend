package com.go2geda.Go2GedaApp.controller;

import com.go2geda.Go2GedaApp.data.models.Commuter;
import com.go2geda.Go2GedaApp.data.models.Driver;
import com.go2geda.Go2GedaApp.data.models.Review;
import com.go2geda.Go2GedaApp.data.models.Trip;
import com.go2geda.Go2GedaApp.dtos.request.AdminRegistrationRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@CrossOrigin("*")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerAdmin(@RequestBody AdminRegistrationRequest adminRegistrationRequest){
        return new  ResponseEntity<RegisterUserResponse>(adminService.registerAdmin(adminRegistrationRequest), HttpStatus.CREATED);
    }
    @GetMapping("/findAllCommuters")
    public ResponseEntity<List<Commuter>> findAllCommuters(){
        return new ResponseEntity<>(adminService.findAllCommuter(),HttpStatus.OK);
    }
    @GetMapping("/findAllDrivers")
    public ResponseEntity<List<Driver>> findAllDrivers(){
        return new ResponseEntity<>(adminService.findAllDriver(),HttpStatus.OK);
    }
    @GetMapping("/commuter/{commuterId}")
    public ResponseEntity<Commuter> findCommuterById(@PathVariable Long commuterId){
        return new ResponseEntity<>(adminService.findCommuterById(commuterId),HttpStatus.OK);
    }
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Driver> findDriverById(@PathVariable Long driverId){
        return new ResponseEntity<>(adminService.findDriverById(driverId),HttpStatus.OK);
    }
    @PostMapping("/suspendCommuterAccount/{commuterId}")
    public ResponseEntity<OkResponse> suspendCommuterAccount(@PathVariable Long commuterId){
        return new ResponseEntity<>(adminService.suspendCommuterAccount(commuterId),HttpStatus.OK);
    }
    @PostMapping("/suspendDriverAccount.{driverId}")
    public ResponseEntity<OkResponse> suspendDriverAccount(@PathVariable Long driverId){
        return new ResponseEntity<>(adminService.suspendDriverAccount(driverId),HttpStatus.OK);
    }
    @GetMapping("findDriverReview/{driverId}")
    public ResponseEntity<List<Review>> findDriversReview(@PathVariable Long driverId){
        return new ResponseEntity<>(adminService.findReviewByDriverId(driverId),HttpStatus.OK);
    }
    @GetMapping("/findCommuterReview/{commuterId}")
    public ResponseEntity<List<Review>> findCommutersReview(@PathVariable Long commuterId){
        return new ResponseEntity<>(adminService.findReviewByCommuterId(commuterId),HttpStatus.OK);
    }
    @GetMapping("/getAllReviews")
    public ResponseEntity<List<Review>> getAllReviews(){
        return new ResponseEntity<>(adminService.getAllReviews(),HttpStatus.OK);
    }
    @GetMapping("/findAllTrips")
    public ResponseEntity<List<Trip>> findAllTrips(){
        return new ResponseEntity<>(adminService.findAllTrip(),HttpStatus.OK);
    }
    @GetMapping("/findAllAvailableTrip")
    public ResponseEntity<List<Trip>> findAllAvailableTrip(){
        return new ResponseEntity<>(adminService.findAllAvailableTrip(),HttpStatus.OK);
    }
    @GetMapping("/findAllCompletedTrips")
    public ResponseEntity<List<Trip>> findAllCompletedTrips(){
        return new ResponseEntity<>(adminService.getAllTripHistory(),HttpStatus.OK);
    }
}
