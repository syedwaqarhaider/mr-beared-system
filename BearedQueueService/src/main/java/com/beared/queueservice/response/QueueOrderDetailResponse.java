package com.beared.queueservice.response;

import com.beared.queueservice.dto.ServiceDTO;
import com.beared.queueservice.enums.QueueOrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QueueOrderDetailResponse {
    private Long id;
    private Long queueId;
    private Long shopId;
    private Long userId;
    private String userName; // From User Microservice
    private Integer estimatedTimeToComplete;
    private QueueOrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ServiceDTO> services; // Names from Service Microservice
}
