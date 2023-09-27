package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.Commuter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuterRepository extends JpaRepository<Commuter,Long> {
}
