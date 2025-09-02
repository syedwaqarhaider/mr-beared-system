package com.beard.walletservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

// DiscountRule.java
@Entity
@Table(name="discount_rules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountRule {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long shopId;
    private String ruleType; // VISIT_BASED, TIME_BASED, AMOUNT_BASED, TIER_BASED
    private Integer conditionValue;
    private BigDecimal discountPercent;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist void onCreate(){ createdAt = updatedAt = LocalDateTime.now(); }
    @PreUpdate void onUpdate(){ updatedAt = LocalDateTime.now(); }
}
