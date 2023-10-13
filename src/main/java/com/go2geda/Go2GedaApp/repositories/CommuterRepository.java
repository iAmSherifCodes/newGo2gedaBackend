package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.Commuter;
import com.go2geda.Go2GedaApp.data.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommuterRepository extends JpaRepository<Commuter,Long> {

    @Query(value = """
            select d from Commuter d
            where d.user.basicInformation.email = :email
            """)
    Optional<Commuter> findCommuterByEmail(String email);
    Optional<Commuter> findByUserBasicInformationEmail(String email);
}
