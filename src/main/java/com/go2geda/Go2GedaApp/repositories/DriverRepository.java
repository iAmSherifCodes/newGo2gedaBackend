package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver,Long> {
    @Query(value = """
            select d from Driver d
            where d.user.basicInformation.email = :email
            """)
    Optional<Driver> findDriverByEmail(String email);
}
