package com.beard.walletservice.clients;

import com.beard.walletservice.responses.ApiResponse;
import com.beard.walletservice.responses.QueueOrderLite;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="BearedQueueService", url="${queue.service.url}") // e.g. http://localhost:8083
public interface QueueClient {
    @GetMapping("/api/queue-orders/{id}")
    ApiResponse<QueueOrderLite> getOrderLite(@PathVariable Long id);
    // QueueOrderLite should include: id, userId, totalPrice, status, createdAt, shopId
}
