package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.Commuter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommuterRepository extends JpaRepository<Commuter,Long> {
    Optional<Commuter> findByUserBasicInformationEmail(String email);
}
