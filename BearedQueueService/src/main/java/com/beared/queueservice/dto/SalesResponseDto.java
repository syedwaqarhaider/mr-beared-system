package com.beared.queueservice.dto;

import com.beared.queueservice.model.QueueOrder;
import com.beared.queueservice.response.QueueOrderResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesResponseDto {
    private List<QueueOrderResponse> orders;
    private BigDecimal totalSales;
}
