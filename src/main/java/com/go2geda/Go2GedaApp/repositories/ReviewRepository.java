package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.Review;
import com.go2geda.Go2GedaApp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ReviewRepository extends JpaRepository<Review,Long> {

}
