package com.beared.shopservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BarberServicesDTO {
    private Long serviceId;
    private String serviceName;
    private String description;
}