package com.beard.walletservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// ReferralReward.java
@Entity
@Table(name="referral_rewards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferralReward {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long referrerUserId;
    private Long referredUserId;
    private Boolean granted;

    private LocalDateTime createdAt;

    @PrePersist void onCreate(){ createdAt = LocalDateTime.now(); }
}
