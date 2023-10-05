package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.*;
import com.go2geda.Go2GedaApp.dtos.request.AdminRegistrationRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RegisterUserResponse;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;
import com.go2geda.Go2GedaApp.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImplementation implements AdminService{
    private final DriverRepository driverRepository;
    private final CommuterRepository commuterRepository;
    private final ReviewRepository reviewRepository;
    private final AdminRepository adminRepository;
    private final TripRepository tripRepository;


    @Override
    public RegisterUserResponse registerAdmin(AdminRegistrationRequest adminRegistrationRequest) {
        Admin admin = new Admin();
        admin.setFirstName(adminRegistrationRequest.getFirstName());
        admin.setLastName(adminRegistrationRequest.getLastName());
        admin.setEmail(adminRegistrationRequest.getEmail());
        admin.setPhoneNumber(adminRegistrationRequest.getPhoneNumber());
        admin.setPassword(adminRegistrationRequest.getPassword());
        Admin savedAdmin = adminRepository.save(admin);
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setFirstName(savedAdmin.getFirstName());
        registerUserResponse.setLastName(savedAdmin.getLastName());
        registerUserResponse.setEmail(savedAdmin.getEmail());
        registerUserResponse.setPhoneNumber(savedAdmin.getPhoneNumber());
        registerUserResponse.setPassword(savedAdmin.getPassword());
        return registerUserResponse;
    }

    @Override
    public List<Commuter> findAllCommuter() {
        List<Commuter> allCommuters = commuterRepository.findAll();
        return allCommuters;
    }

    @Override
    public List<Driver> findAllDriver() {
        List<Driver> allDrivers = driverRepository.findAll();
        return allDrivers;
    }

    @Override
    public Commuter findCommuterById(Long commuterId) {
        Optional<Commuter> commuter = commuterRepository.findById(commuterId);
        Commuter foundCommuter = null;
        try {
            foundCommuter = commuter.orElseThrow(()-> new NotFoundException("Commuter Not Found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return foundCommuter;
    }

    @Override
    public Driver findDriverById(Long driverId) {
        Optional<Driver> driver = driverRepository.findById(driverId);
        Driver foundDriver = null;
        try {
            foundDriver = driver.orElseThrow(()-> new NotFoundException(" Driver Not Found"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return foundDriver;
    }

    @Override
    public OkResponse suspendDriverAccount(Long driverId) {
        Driver foundDriver = findDriverById(driverId);
        foundDriver.getUser().setActive(false);
        driverRepository.save(foundDriver);
        OkResponse okResponse = new OkResponse();
        okResponse.setMessage("Account Suspended Successfully");
        return okResponse;
    }

    @Override
    public OkResponse suspendCommuterAccount(Long commuterId) {
        Commuter foundCommuter = findCommuterById(commuterId);
        foundCommuter.getUser().setActive(false);
        commuterRepository.save(foundCommuter);
        OkResponse okResponse = new OkResponse();
        okResponse.setMessage("Account Suspended Successfully");
        return okResponse;
    }

    @Override
    public List<Review> findReviewByCommuterId(Long commuterId) {
        Commuter foundCommuter = findCommuterById(commuterId);
        List<Review> commuterReviews = foundCommuter.getUser().getReviews();
        return commuterReviews;
    }

    @Override
    public List<Review> findReviewByDriverId(Long driverId) {
        Driver foundDriver = findDriverById(driverId);
        List<Review> driverReviews = foundDriver.getUser().getReviews();
        return driverReviews;
    }

    @Override
    public List<Review> getAllReviews() {
        List<Review> allReviews = reviewRepository.findAll();
        return allReviews;
    }

    @Override
    public List<Trip> findAllTrip() {
        List<Trip> allTrips = tripRepository.findAll();
        return allTrips;
    }

    @Override
    public List<Trip> findAllAvailableTrip() {
        List<Trip> availableTrips = new ArrayList<>();
        List<Trip> foundTrips = tripRepository.findAll();
        boolean hasCreatedTrips = false;
        for (int i = 0; i < foundTrips.size(); i++) {
            if (foundTrips.get(i).getTripStatus().equals(TripStatus.CREATED)) {
                availableTrips.add(foundTrips.get(i));
                hasCreatedTrips = true;
            }
        }
        if (!hasCreatedTrips) {
            try {
                throw new NotFoundException("No Available Trip For the Route");
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return availableTrips;
    }

    @Override
    public List<Trip> getAllTripHistory() {
        List<Trip> completedTrips = new ArrayList<>();
        List<Trip> foundTrips = tripRepository.findAll();
        boolean hasCreatedTrips = false;
        for (int i = 0; i < foundTrips.size(); i++) {
            if (foundTrips.get(i).getTripStatus().equals(TripStatus.COMPLETED)) {
                completedTrips.add(foundTrips.get(i));
                hasCreatedTrips = true;
            }
        }
        if (!hasCreatedTrips) {
            try {
                throw new NotFoundException("No Available Trip For the Route");
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return completedTrips;

    }
}
