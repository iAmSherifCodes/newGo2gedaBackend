package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Long> {
}
