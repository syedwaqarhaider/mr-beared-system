package com.beared.queueservice.repository;

import com.beared.queueservice.model.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderServiceRepository extends JpaRepository<OrderService, Long> {
    List<OrderService> findByQueueOrder_Id(Long queueOrderId);
}
