package com.beared.queueservice.repository;
import com.beared.queueservice.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {
    boolean existsByQueueName(String queueName);
}
