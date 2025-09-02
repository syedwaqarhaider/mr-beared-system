package com.beard.walletservice.repository;

import com.beard.walletservice.model.PointsExpiration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PointsExpirationRepository extends JpaRepository<PointsExpiration, Long> {
    List<PointsExpiration> findByUserIdAndExpiresAtAfterOrderByExpiresAtAsc(Long userId, LocalDateTime now);
}