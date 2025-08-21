package com.beared.queueservice.model;

import com.beared.queueservice.enums.QueueOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "queue_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long queueId;
    private Long shopId;
    private Long userId;

    private Integer estimatedTimeToComplete;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private QueueOrderStatus status;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "queueOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderService> orderServices;

    @PrePersist
    public void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}