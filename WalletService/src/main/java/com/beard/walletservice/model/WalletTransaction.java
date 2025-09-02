package com.beard.walletservice.model;

import com.beard.walletservice.enums.WalletTxnType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// WalletTransaction.java
@Entity
@Table(name="wallet_transactions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","idempotency_key"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransaction {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private WalletTxnType type; // EARN/REDEEM/EXPIRE/ADJUST/REVERSAL

    private Long points;

    private BigDecimal amount;

    private String description;

    private String referenceId;     // e.g. queue order id
    private String idempotencyKey;  // for retry safety

    private LocalDateTime createdAt;

    @PrePersist void onCreate(){ createdAt = LocalDateTime.now(); }
}

