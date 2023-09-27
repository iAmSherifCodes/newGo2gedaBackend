package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.data.models.Trip;
import com.go2geda.Go2GedaApp.dtos.request.CreateTripRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;
import com.go2geda.Go2GedaApp.exceptions.NotFoundException;

import java.util.List;

public interface TripService {
    List<Trip> searchTripByFrom(String from) throws NotFoundException;
    List<Trip> searchTripByTo(String to) throws NotFoundException;
    OkResponse createTrip(CreateTripRequest createTripRequest, String email) throws NotFoundException;
    OkResponse cancelTrip(long tripId) throws NotFoundException;

    OkResponse bookTrip (Long commuterId, Long TripId) throws NotFoundException;
    OkResponse acceptTripRequest(Long commuterId,Long id) throws NotFoundException;
    OkResponse rejectTripResponse(Long driverId,Long tripId) throws NotFoundException;

    OkResponse startTrip(Long tripId) throws NotFoundException;
    OkResponse endTrip(Long tripId) throws NotFoundException;

    Trip viewTrip (Long tripId) throws NotFoundException;

    List<Trip> viewCommuterBookedTrips(Long commuterId);
}
