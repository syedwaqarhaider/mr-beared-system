package com.beared.queueservice.controller;

import com.beared.queueservice.model.Queue;
import com.beared.queueservice.response.ApiResponse;
import com.beared.queueservice.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queues")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @PostMapping
    public ResponseEntity<ApiResponse<Queue>> createQueue(@RequestBody Queue queue) {
        Queue created = queueService.createQueue(queue);
        return ResponseEntity.ok(new ApiResponse<>(true, "Queue created successfully", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Queue>> getQueue(@PathVariable Long id) {
        Queue queue = queueService.getQueueById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Queue fetched successfully", queue));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Queue>>> getAllQueues() {
        List<Queue> queues = queueService.getAllQueues();
        return ResponseEntity.ok(new ApiResponse<>(true, "All queues fetched", queues));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteQueue(@PathVariable Long id) {
        queueService.deleteQueue(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Queue deleted successfully"));
    }
}