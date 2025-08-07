package com.beared.queueservice.dto;
import lombok.Data;
import java.util.List;

@Data
public class QueueOrderRequest {
    private Long queueId;
    private Long shopId;
    private Long userId;
    private Integer estimatedTimeToComplete;
    private List<Long> serviceIds;
}
