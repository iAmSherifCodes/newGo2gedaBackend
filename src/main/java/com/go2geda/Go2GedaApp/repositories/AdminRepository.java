package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
