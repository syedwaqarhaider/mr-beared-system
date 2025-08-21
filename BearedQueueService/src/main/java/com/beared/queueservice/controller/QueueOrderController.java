package com.beared.queueservice.controller;

import com.beared.queueservice.dto.QueueOrderRequest;
import com.beared.queueservice.dto.SalesResponseDto;
import com.beared.queueservice.enums.QueueOrderStatus;
import com.beared.queueservice.response.QueueOrderDetailResponse;
import com.beared.queueservice.response.QueueOrderResponse;
import com.beared.queueservice.response.ApiResponse;
import com.beared.queueservice.service.QueueOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/queue-orders")
@RequiredArgsConstructor
public class QueueOrderController {

    private final QueueOrderService queueOrderService;

    @PostMapping
    public ResponseEntity<ApiResponse<QueueOrderResponse>> createQueueOrder(@RequestBody QueueOrderRequest request) {
        QueueOrderResponse response = queueOrderService.createQueueOrder(request);
        return ResponseEntity.ok(ApiResponse.<QueueOrderResponse>builder()
                .success(true)
                .message("Queue order created successfully")
                .data(response)
                .build());
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<?>> updateStatus(@PathVariable Long id, @RequestParam QueueOrderStatus status) {
        return ResponseEntity.ok(queueOrderService.updateOrderStatus(id, status));
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<ApiResponse<List<QueueOrderResponse>>> getOrdersByShopAndStatus(
            @PathVariable Long shopId,
            @RequestParam(required = false) QueueOrderStatus status) {

        List<QueueOrderResponse> orders = queueOrderService.getOrdersByShopAndStatus(shopId, status);
        if (orders.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Orders not found", orders));

        } else {
            return ResponseEntity.ok(new ApiResponse<>(true, "Orders fetched successfully", orders));

        }
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<ApiResponse<QueueOrderDetailResponse>> getOrderDetail(@PathVariable Long id) {
        QueueOrderDetailResponse detail = queueOrderService.getOrderDetail(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order detail fetched", detail));
    }

    @GetMapping("/{shopId}/today")
    public ResponseEntity<ApiResponse<Map<String, BigDecimal>>> getTodaySales(@PathVariable Long shopId) {
        Map<String, BigDecimal> sales = queueOrderService.getTodaySales(shopId);
        return ResponseEntity.ok(ApiResponse.<Map<String, BigDecimal>>builder()
                .success(true)
                .message("Today's sales calculated successfully")
                .data(sales)
                .build());
    }

    @GetMapping("/sales/{shopId}")
    public ResponseEntity<ApiResponse<SalesResponseDto>> getSalesReport(
            @PathVariable Long shopId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        SalesResponseDto result = queueOrderService.getCompletedOrdersWithSales(shopId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.<SalesResponseDto>builder()
                        .success(true)
                        .message("Sale History Fetched")
                        .data(result)
                        .build());
    }
}