package com.beard.walletservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// PointsExpiration.java (optional)
@Entity
@Table(name="points_expirations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsExpiration {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long points;
    private Long consumed; // used from this bucket
    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;

    @PrePersist void onCreate(){ createdAt = LocalDateTime.now(); }
}
