package com.beared.queueservice.response;

import com.beared.queueservice.enums.QueueOrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class QueueOrderResponse {
    private Long id;
    private Long queueId;
    private Long shopId;
    private Long userId;
    private Integer estimatedTimeToComplete;
    private QueueOrderStatus status;
    private List<Long> serviceIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}