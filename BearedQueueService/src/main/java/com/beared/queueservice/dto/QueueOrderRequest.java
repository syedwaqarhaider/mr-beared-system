package com.beared.queueservice.dto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class QueueOrderRequest {
    private Long queueId;
    private Long shopId;
    private Long userId;
    private Integer estimatedTimeToComplete;
    private BigDecimal totalPrice;
    private List<Long> serviceIds;
}
