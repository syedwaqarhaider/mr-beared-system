package com.beared.queueservice.repository;

import com.beared.queueservice.enums.QueueOrderStatus;
import com.beared.queueservice.model.QueueOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueueOderRepository extends JpaRepository<QueueOrder, Long> {

    List<QueueOrder> findByShopId(Long shopId);
    List<QueueOrder> findByShopIdAndStatus(Long shopId, QueueOrderStatus status);
}
