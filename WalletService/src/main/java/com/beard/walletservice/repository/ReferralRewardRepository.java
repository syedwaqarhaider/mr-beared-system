package com.beard.walletservice.repository;

import com.beard.walletservice.model.ReferralReward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferralRewardRepository extends JpaRepository<ReferralReward, Long> {
    boolean existsByReferrerUserIdAndReferredUserId(Long referrer, Long referred);
}
