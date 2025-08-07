package com.beared.queueservice.mapper;
import com.beared.queueservice.response.QueueOrderResponse;
import com.beared.queueservice.model.QueueOrder;
import com.beared.queueservice.model.OrderService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QueueOrderMapper {
    public static QueueOrderResponse toResponse(QueueOrder order) {
        List<Long> serviceIds = order.getOrderServices()
                .stream()
                .map(OrderService::getServiceId)
                .collect(Collectors.toList());

        return QueueOrderResponse.builder()
                .id(order.getId())
                .queueId(order.getQueueId())
                .shopId(order.getShopId())
                .userId(order.getUserId())
                .estimatedTimeToComplete(order.getEstimatedTimeToComplete())
                .status(order.getStatus())
                .serviceIds(serviceIds)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}