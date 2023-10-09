package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.*;
import com.go2geda.Go2GedaApp.dtos.request.AcceptAndRejectRequest;
import com.go2geda.Go2GedaApp.dtos.request.CreateTripRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;
import com.go2geda.Go2GedaApp.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TripServiceImplementation implements TripService {

    private final DriverRepository driverRepository;
    private final CommuterRepository commuterRepository;
    private final TripRepository tripRepository;
    private final NotificationRepository notificationRepository;


    @Override
    public List<Trip> searchTripByFrom(String from) throws NotFoundException {
        List<Trip> availableTrips = new ArrayList<>();
        List<Trip> foundTrips = tripRepository.findTripByPickup(from);
        boolean hasCreatedTrips = false;
        for (int i = 0; i < foundTrips.size(); i++) {
            if (foundTrips.get(i).getTripStatus().equals(TripStatus.CREATED)) {
                availableTrips.add(foundTrips.get(i));
                hasCreatedTrips = true;
            }
        }
        if (!hasCreatedTrips) {
            throw new NotFoundException("No Available Trip For the Route");
        }
        return availableTrips;
    }


    @Override
    public List<Trip> searchTripByTo(String to) throws NotFoundException {
        List<Trip> availableTrips = new ArrayList<>();
        List<Trip> foundTrips = tripRepository.findTripByDestination(to);
        boolean hasCreatedTrips = false;
        for (int i = 0; i < foundTrips.size(); i++) {
            if (foundTrips.get(i).getTripStatus().equals(TripStatus.CREATED)) {
                availableTrips.add(foundTrips.get(i));
                hasCreatedTrips = true;
            }
        }
        if (!hasCreatedTrips) {
            throw new NotFoundException("No Available Trip For the Route");
        }

        return availableTrips;
    }

    @Override
    public OkResponse createTrip(CreateTripRequest createTripRequest) throws NotFoundException {
        Trip trip = new Trip();
        Optional<Driver> driver= driverRepository.findDriverByEmail(createTripRequest.getEmail());
        Driver foundDriver = driver.orElseThrow(()->new NotFoundException("Driver with this email does not exist"));
        trip.setPickup(createTripRequest.getFrom());
        trip.setDestination(createTripRequest.getTo());
        trip.setPricePerSeat(createTripRequest.getPricePerSeat());
        trip.setNumberOfSeatsAvailable(createTripRequest.getNumberOfSeats());
        trip.setPickUpTime(createTripRequest.getPickUpTime());
        trip.setEndTime(createTripRequest.getEndTime());
        trip.setStartTime(createTripRequest.getStartTime());
        trip.setTripStatus(TripStatus.CREATED);
        trip.setDriver(foundDriver);
        trip.setTripStatus(TripStatus.valueOf(createTripRequest.getTripStatus()));


        Trip savedTrip =tripRepository.save(trip);


        OkResponse okResponse = new OkResponse();
        okResponse.setId(savedTrip.getId());
        okResponse.setMessage("Trip has been created successfully");
        return okResponse;
    }

    @Override
    public OkResponse cancelTrip(long tripId) throws NotFoundException {
        Optional<Trip> trip = tripRepository.findById(tripId);
        Trip foundTrip = trip.orElseThrow(()->new NotFoundException("Trip not found"));
        foundTrip.setTripStatus(TripStatus.CANCELED);
        tripRepository.save(foundTrip);
        OkResponse okResponse = new OkResponse();
        okResponse.setMessage("Trip canceled Successfully");
        return okResponse;
    }

    @Override
    public OkResponse bookTrip(AcceptAndRejectRequest acceptAndRejectRequest) throws NotFoundException {

        Notification  notification = new Notification();
        Optional<Commuter> commuter = commuterRepository.findById(acceptAndRejectRequest.getCommuterId());
        Commuter foundCommuter = commuter.orElseThrow(()->new NotFoundException("Commuter not found"));
        Trip foundTrip = viewTrip(acceptAndRejectRequest.getTripId());
        notification.setSenderId(acceptAndRejectRequest.getCommuterId());
        notification.setReceiverId(foundTrip.getDriver().getId());
        notification.setMessage(foundCommuter
                .getUser()
                .getBasicInformation()
                .getFirstName()
                + " "
                + foundCommuter
                .getUser()
                .getBasicInformation()
                .getLastName() + "requested to join trip");
        Notification savedNotification = notificationRepository.save(notification);
        Driver driver = foundTrip.getDriver();
        driver.getNotificationList().add(savedNotification);
        driverRepository.save(driver);
        OkResponse okResponse = new OkResponse();
        okResponse.setMessage("trip booked successfully");
        return okResponse;
    }

    @Override
    public OkResponse acceptTripRequest(AcceptAndRejectRequest acceptAndRejectRequest) throws NotFoundException {

        Optional<Commuter> commuter = commuterRepository.findById(acceptAndRejectRequest.getCommuterId());
        Commuter foundCommuter = commuter.orElseThrow(()->new NotFoundException("Commuter not found"));

        Optional<Trip> trip = tripRepository.findById(acceptAndRejectRequest.getTripId());
        Trip foundTrip = trip.orElseThrow(()->new NotFoundException("Trip not found"));
        foundTrip.getCommuter().add(foundCommuter);
        tripRepository.save(foundTrip);

        Notification notification = new Notification();
        notification.setReceiverId(foundCommuter.getId());
        notification.setSenderId(foundTrip.getDriver().getId());
        notification.setMessage("you request to join  has been accepted");
        Notification savedNotification = notificationRepository.save(notification);
        foundCommuter.getNotifications().add(savedNotification);
        commuterRepository.save(foundCommuter);

        OkResponse okResponse = new OkResponse();
        okResponse.setMessage("successful");
        return okResponse;
    }

    @Override
    public OkResponse rejectTripRequest(AcceptAndRejectRequest acceptAndRejectRequest) throws NotFoundException {

        Optional<Commuter> commuter = commuterRepository.findById(acceptAndRejectRequest.getCommuterId());
        Commuter foundCommuter = commuter.orElseThrow(()->new NotFoundException("Commuter not found"));
        Optional<Trip> trip = tripRepository.findById(acceptAndRejectRequest.getTripId());
        Trip foundTrip = trip.orElseThrow(()->new NotFoundException("Trip not found"));

        Notification notification = new Notification();
        notification.setReceiverId(foundCommuter.getId());
        notification.setSenderId(foundTrip.getDriver().getId());
        notification.setMessage("you request to join "+ foundTrip.getPickup() + foundTrip.getDestination() +" has been rejected");

        Notification savedNotification = notificationRepository.save(notification);
        foundCommuter.getNotifications().add(savedNotification);


        OkResponse okResponse = new OkResponse();
        okResponse.setMessage("successful");
        return okResponse;
    }

    @Override
    public OkResponse startTrip(Long tripId) throws NotFoundException {
        Optional<Trip> trip = tripRepository.findById(tripId);
        Trip foundTrip = trip.orElseThrow(()->new NotFoundException("Trip not found"));
        foundTrip.setTripStatus(TripStatus.STARTED);
        tripRepository.save(foundTrip);

        OkResponse okResponse = new OkResponse();
        okResponse.setMessage("trip started");
        return okResponse;
    }

    @Override
    public OkResponse endTrip(Long tripId) throws NotFoundException {
        Optional<Trip> trip = tripRepository.findById(tripId);
        Trip foundTrip = trip.orElseThrow(()->new NotFoundException("Trip not found"));
        foundTrip.setTripStatus(TripStatus.COMPLETED);
        tripRepository.save(foundTrip);

        OkResponse okResponse = new OkResponse();
        okResponse.setMessage("trip Completed");
        return okResponse;
    }

    @Override
    public Trip viewTrip(Long tripId) throws NotFoundException {
        Optional<Trip> trip = tripRepository.findById(tripId);
        Trip foundTrip = trip.orElseThrow(()->new NotFoundException("Trip not found"));
        return foundTrip;
    }

    @Override
    public List<Trip> viewCommuterBookedTrips(Long commuterId) throws NotFoundException {
        List<Trip> commuterBookedTrip = new ArrayList<>();
        List<Trip> allTrip = tripRepository.findAll();
        boolean bookedTrip = false;
        for (int i = 0; i < allTrip.size(); i++) {
            if (allTrip.get(i).getTripStatus()==TripStatus.CREATED){
                for (int j = 0; j < allTrip.get(i).getCommuter().size(); j++) {
                    if(allTrip.get(i).getCommuter().get(j).getId().equals(commuterId)){
                        commuterBookedTrip.add(allTrip.get(i));
                        bookedTrip = true;
                    }
                }
            }
        }
        if (!bookedTrip) {
            throw new NotFoundException("No Available Trip For the Route");
        }
        return commuterBookedTrip;
    }
}
