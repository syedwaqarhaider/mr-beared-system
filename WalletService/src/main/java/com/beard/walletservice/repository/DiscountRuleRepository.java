package com.beard.walletservice.repository;

import com.beard.walletservice.model.DiscountRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRuleRepository extends JpaRepository<DiscountRule, Long> {
    List<DiscountRule> findByShopIdAndActiveTrue(Long shopId);
}
