package com.go2geda.Go2GedaApp.repositories;

import com.go2geda.Go2GedaApp.data.models.PayStackCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PayStackCustomerRepository extends JpaRepository<PayStackCustomer,Long> {
    Optional<PayStackCustomer> findByCustomerCode(String customerCode);

}
