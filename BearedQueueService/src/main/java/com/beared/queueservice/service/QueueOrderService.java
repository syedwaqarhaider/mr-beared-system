package com.beared.queueservice.service;
import com.beared.queueservice.clients.ShopClient;
import com.beared.queueservice.clients.UserClient;
import com.beared.queueservice.dto.QueueOrderRequest;
import com.beared.queueservice.dto.ServiceDTO;
import com.beared.queueservice.dto.UserDTO;
import com.beared.queueservice.exception.ResourceNotFoundException;
import com.beared.queueservice.repository.OrderServiceRepository;
import com.beared.queueservice.response.ApiResponse;
import com.beared.queueservice.response.QueueOrderDetailResponse;
import com.beared.queueservice.response.QueueOrderResponse;
import com.beared.queueservice.enums.QueueOrderStatus;
import com.beared.queueservice.mapper.QueueOrderMapper;
import com.beared.queueservice.model.OrderService;
import com.beared.queueservice.model.QueueOrder;
import com.beared.queueservice.repository.QueueOderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueueOrderService {

    private final QueueOderRepository queueOrderRepository;
    private final OrderServiceRepository orderServiceRepository;
    private final ShopClient shopClient;
    private final UserClient userClient;

    @Transactional
    public QueueOrderResponse createQueueOrder(QueueOrderRequest request) {
        QueueOrder order = QueueOrder.builder()
                .queueId(request.getQueueId())
                .shopId(request.getShopId())
                .userId(request.getUserId())
                .estimatedTimeToComplete(request.getEstimatedTimeToComplete())
                .status(QueueOrderStatus.WAITING)
                .build();

        List<OrderService> services = request.getServiceIds().stream()
                .map(serviceId -> OrderService.builder()
                        .serviceId(serviceId)
                        .queueOrder(order)
                        .build())
                .collect(Collectors.toList());

        order.setOrderServices(services);
        QueueOrder saved = queueOrderRepository.save(order);
        return QueueOrderMapper.toResponse(saved);
    }

    @Transactional
    public ApiResponse<QueueOrder> updateOrderStatus(Long id, QueueOrderStatus newStatus) {
        QueueOrder order = queueOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found" + id));
        order.setStatus(newStatus);

        try {
            QueueOrder queueOrder = queueOrderRepository.save(order);

            String msg = "Order marked as " + newStatus;

            return new ApiResponse<>(true, msg,null);

        }
        catch (Exception e)
        {
            return new ApiResponse<>(false, "Order not updated",null);
        }

    }

    public List<QueueOrderResponse> getOrdersByShopAndStatus(Long shopId, QueueOrderStatus status) {
        List<QueueOrder> orders;

        if (status != null) {
            orders = queueOrderRepository.findByShopIdAndStatus(shopId, status);
        } else {
            orders = queueOrderRepository.findByShopId(shopId);
        }

        return orders.stream()
                .map(QueueOrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    public QueueOrderDetailResponse getOrderDetail(Long orderId) {
        QueueOrder order = queueOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Fetch order services
        List<OrderService> orderServices = orderServiceRepository.findByQueueOrder_Id(orderId);
        List<Long> serviceIds = orderServices.stream()
                .map(OrderService::getServiceId)
                .collect(Collectors.toList());

        // Call user service via Feign
        UserDTO user = userClient.getUserById(order.getUserId());

        // Call Shop microservice via Feign
        List<ServiceDTO> services = serviceIds.isEmpty() ? Collections.emptyList() :
                shopClient.getServicesByIds(serviceIds);

        // Prepare response
        QueueOrderDetailResponse response = new QueueOrderDetailResponse();
        response.setId(order.getId());
        response.setQueueId(order.getQueueId());
        response.setShopId(order.getShopId());
        response.setUserId(order.getUserId());
        response.setUserName(user.getName());
        response.setEstimatedTimeToComplete(order.getEstimatedTimeToComplete());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());
        response.setServices(services);

        return response;
    }
}
