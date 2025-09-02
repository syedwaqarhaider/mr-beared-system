package com.beard.walletservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// Wallet.java
@Entity
@Table(name="wallets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    private Long userId;

    private Long balancePoints;
    private Long lifetimeEarned;
    private Long lifetimeRedeemed;
    private String tier;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate(){ createdAt = updatedAt = LocalDateTime.now(); }
    @PreUpdate
    void onUpdate(){ updatedAt = LocalDateTime.now(); }
}
