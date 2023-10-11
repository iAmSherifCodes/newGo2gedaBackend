package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip,Long> {
    List<Trip> findTripByPickup (String pickUp);
    List<Trip> findTripByDestination (String destination);
    List<Trip> findTripByPickupAndDestination (String pickup, String destination);

    List<Trip> findByDriver_Id(Long driverId);
    @Query(value = """
            select t from Trip t
            where t.tripStatus = "COMPLETED" or t.tripStatus =  "CANCELED"
""")
    List<Trip> findByTripStatus();
}
