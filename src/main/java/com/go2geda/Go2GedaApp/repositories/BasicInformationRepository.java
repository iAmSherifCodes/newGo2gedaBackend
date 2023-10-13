package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.BasicInformation;
import com.go2geda.Go2GedaApp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasicInformationRepository extends JpaRepository<BasicInformation, Long> {
    boolean existsByEmail(String email);

    Optional<BasicInformation> findByEmail(String email);

}
