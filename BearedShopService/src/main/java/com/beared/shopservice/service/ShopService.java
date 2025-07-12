package com.beared.shopservice.service;

import com.beared.shopservice.model.Shop;
import com.beared.shopservice.repository.ShopOwnerRepository;
import com.beared.shopservice.repository.ShopRepository;
import com.beared.shopservice.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopOwnerRepository shopOwnerRepository;

    public ApiResponse<Shop> createShop(Shop shop) {
        if (shop.getOwner() == null || shop.getOwner().getId() == null) {
            throw new IllegalArgumentException("Owner ID is required.");
        }

        boolean ownerExists = shopOwnerRepository.existsById(shop.getOwner().getId());
        if (!ownerExists) {
            throw new IllegalArgumentException("Shop owner not found.");
        }

        Shop saved = shopRepository.save(shop);
        return new ApiResponse<>(true, "Shop created", saved);
    }

    public ApiResponse<List<Shop>> getAllShops() {
        return new ApiResponse<>(true, "All shops", shopRepository.findAll());
    }

    public ApiResponse<Shop> updateShop(Long id, Shop updated) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found."));

        shop.setShopName(updated.getShopName());
        shop.setShopAddress(updated.getShopAddress());
        shop.setLatitude(updated.getLatitude());
        shop.setLongitude(updated.getLongitude());
        shop.setStatus(updated.getStatus());
        shop.setPictureUrl(updated.getPictureUrl());

        Shop saved = shopRepository.save(shop);
        return new ApiResponse<>(true, "Shop updated", saved);
    }

    public ApiResponse<String> deleteShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found."));

        shopRepository.delete(shop);
        return new ApiResponse<>(true, "Shop deleted", null);
    }
}
