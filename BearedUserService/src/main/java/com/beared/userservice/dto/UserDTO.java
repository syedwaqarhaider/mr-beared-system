package com.beared.userservice.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String fullName;
    private String email;
}