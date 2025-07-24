package com.beared.shopservice.repository;

import com.beared.shopservice.model.Shop2Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface Shop2ServiceRepository extends JpaRepository<Shop2Service, Long> {
    Optional<Shop2Service> findByShop_ShopIdAndServices_ServiceId(Long shopId, Long serviceId);
    List<Shop2Service> findByShop_ShopId(Long shopId);

}
