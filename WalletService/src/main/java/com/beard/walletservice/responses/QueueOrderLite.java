package com.beard.walletservice.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class QueueOrderLite {
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private String status; // COMPLETED etc.
    private LocalDateTime createdAt;
    private Long shopId;
}
