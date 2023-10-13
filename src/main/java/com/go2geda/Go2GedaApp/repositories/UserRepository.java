package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = """
        select d from User d
        where d.basicInformation.email = :email
""")
    Optional<User> findByEmail(String email);
}
