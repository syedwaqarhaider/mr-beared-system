package com.beared.queueservice.dto;

import lombok.Data;

@Data
public class ServiceDTO {
    private Long serviceId;
    private String serviceName;
    private String description;
}
