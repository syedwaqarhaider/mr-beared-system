package com.beared.shopservice.dto;

import lombok.Data;

@Data
public class ShopDTO {
    private Long shopId;
    private String shopName;
    private String pictureUrl;
    private String shopAddress;
    private Double latitude;
    private Double longitude;
    private char status;
    private Double avgRating;

    private Long ownerId;   // just ID, not the full lazy ShopOwner
    private String ownerName; // optional: add if you want
}
