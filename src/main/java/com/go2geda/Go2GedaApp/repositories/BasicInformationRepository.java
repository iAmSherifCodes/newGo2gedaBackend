package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.BasicInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicInformationRepository extends JpaRepository<BasicInformation, Long> {
    boolean existsByEmail(String email);
}
