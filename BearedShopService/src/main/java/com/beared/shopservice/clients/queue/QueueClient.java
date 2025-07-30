package com.beared.shopservice.clients.queue;

import com.beared.shopservice.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "BearedQueueService", url = "${queue.service.url}")
public interface  QueueClient {
    @PostMapping("/api/queues")
    ApiResponse<?> createQueue(@RequestBody QueueRequest request);

}
