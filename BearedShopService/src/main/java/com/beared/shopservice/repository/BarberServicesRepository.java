package com.beared.shopservice.repository;

import com.beared.shopservice.model.BarberServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberServicesRepository extends JpaRepository<BarberServices, Long> {
    boolean existsByServiceName(String serviceName);
}
