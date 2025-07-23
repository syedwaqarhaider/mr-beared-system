package com.beared.shopservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Shop2ServiceDTO {
    private Long id;
    private Long shopId;
    private Long serviceId;
    private String serviceName;
    private String serviceDescription;
    private Double price;
    private Integer durationMinutes;
    private Boolean active;
}

