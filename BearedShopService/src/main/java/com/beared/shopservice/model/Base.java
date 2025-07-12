package com.beared.shopservice.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Base {
    protected LocalDateTime createdAt=LocalDateTime.now();
    protected LocalDateTime updatedAt=LocalDateTime.now();
}
