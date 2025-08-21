package com.beared.queueservice.repository;

import com.beared.queueservice.enums.QueueOrderStatus;
import com.beared.queueservice.model.QueueOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface QueueOderRepository extends JpaRepository<QueueOrder, Long> {

    List<QueueOrder> findByShopIdOrderByCreatedAtAsc(Long shopId);
    List<QueueOrder> findByShopIdAndStatusOrderByCreatedAtAsc(Long shopId, QueueOrderStatus status);
    boolean existsByUserIdAndStatusIn(Long userId, List<QueueOrderStatus> statuses);
    @Query("SELECT COALESCE(SUM(q.totalPrice), 0) FROM QueueOrder q " +
            "WHERE q.shopId = :shopId " +
            "AND q.status IN (:statuses) " +
            "AND q.createdAt >= :startOfDay " +
            "AND q.createdAt < :endOfDay")
    BigDecimal getExpectedSales(@Param("shopId") Long shopId,
                                @Param("statuses") List<QueueOrderStatus> statuses,
                                @Param("startOfDay") LocalDateTime startOfDay,
                                @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COALESCE(SUM(q.totalPrice), 0) FROM QueueOrder q " +
            "WHERE q.shopId = :shopId " +
            "AND q.status = :status " +
            "AND q.createdAt >= :startOfDay " +
            "AND q.createdAt < :endOfDay")
    BigDecimal getConfirmedSales(@Param("shopId") Long shopId,
                                 @Param("status") QueueOrderStatus status,
                                 @Param("startOfDay") LocalDateTime startOfDay,
                                 @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT q FROM QueueOrder q " +
            "WHERE q.shopId = :shopId " +
            "AND q.status = com.beared.queueservice.enums.QueueOrderStatus.COMPLETED " +
            "AND q.createdAt BETWEEN :startDate AND :endDate")
    List<QueueOrder> findCompletedOrdersWithinDateRange(@Param("shopId") Long shopId,
                                                        @Param("startDate") LocalDateTime startDate,
                                                        @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COALESCE(SUM(q.totalPrice), 0) FROM QueueOrder q " +
            "WHERE q.shopId = :shopId " +
            "AND q.status = com.beared.queueservice.enums.QueueOrderStatus.COMPLETED " +
            "AND q.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalSalesWithinDateRange(@Param("shopId") Long shopId,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

}
