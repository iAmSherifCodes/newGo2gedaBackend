package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.Notification;
import com.go2geda.Go2GedaApp.data.models.Trip;
import com.go2geda.Go2GedaApp.dtos.request.AcceptAndRejectRequest;
import com.go2geda.Go2GedaApp.dtos.response.AcceptRequestNotificationResponse;
import com.go2geda.Go2GedaApp.dtos.response.BookingNotificationResponse;
import com.go2geda.Go2GedaApp.dtos.request.CreateTripRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.dtos.response.RejectRequestNotificationResponse;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;
import com.go2geda.Go2GedaApp.exceptions.UserNotFound;

import java.util.List;
import java.util.Set;

public interface TripService {
    List<Trip> searchTripByFromAndTo(String from, String to) throws NotFoundException;
    List<Trip> searchTripByFrom(String from) throws NotFoundException;
    List<Trip> driverTripHistory(Long driverId) throws NotFoundException;g
    List<Trip> commuterTripHistory(Long commuterId) throws NotFoundException;
    List<Trip> searchTripByTo(String to) throws NotFoundException;
    OkResponse createTrip(CreateTripRequest createTripRequest) throws NotFoundException;
    OkResponse cancelTrip(long tripId) throws NotFoundException;
    BookingNotificationResponse bookTrip (AcceptAndRejectRequest acceptAndRejectRequest) throws NotFoundException;
    AcceptRequestNotificationResponse acceptTripRequest(AcceptAndRejectRequest acceptAndRejectRequest) throws NotFoundException;
    RejectRequestNotificationResponse rejectTripRequest(AcceptAndRejectRequest acceptAndRejectRequest) throws NotFoundException;
    OkResponse startTrip(Long tripId) throws NotFoundException;
    OkResponse endTrip(Long tripId) throws NotFoundException;
    Trip viewTrip (Long tripId) throws NotFoundException;
    List<Trip> viewCommuterBookedTrips(Long commuterId) throws NotFoundException;
    List <Trip> getDriversTrips(Long id);
    List<Notification> getTripRequests (Long id);
}
