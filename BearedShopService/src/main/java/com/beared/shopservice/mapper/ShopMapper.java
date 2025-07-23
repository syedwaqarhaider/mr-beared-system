package com.beared.shopservice.mapper;

import com.beared.shopservice.dto.ShopDTO;
import com.beared.shopservice.model.Shop;

public class ShopMapper {
    public static ShopDTO toDTO(Shop shop) {
        ShopDTO dto = new ShopDTO();
        dto.setShopId(shop.getShopId());
        dto.setShopName(shop.getShopName());
        dto.setPictureUrl(shop.getPictureUrl());
        dto.setShopAddress(shop.getShopAddress());
        dto.setLatitude(shop.getLatitude());
        dto.setLongitude(shop.getLongitude());
        dto.setStatus(shop.getStatus());
        dto.setAvgRating(shop.getAvgRating());

        if (shop.getOwner() != null) {
            dto.setOwnerId(shop.getOwner().getId());
            dto.setOwnerName(shop.getOwner().getFullName());
        }

        return dto;
    }
}
