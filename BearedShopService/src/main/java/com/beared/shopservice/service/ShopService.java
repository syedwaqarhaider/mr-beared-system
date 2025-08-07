package com.beared.shopservice.service;

import com.beared.shopservice.clients.queue.QueueClient;
import com.beared.shopservice.clients.queue.QueueRequest;
import com.beared.shopservice.dto.ShopDTO;
import com.beared.shopservice.mapper.ShopMapper;
import com.beared.shopservice.model.Shop;
import com.beared.shopservice.repository.ShopOwnerRepository;
import com.beared.shopservice.repository.ShopRepository;
import com.beared.shopservice.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopOwnerRepository shopOwnerRepository;
    private final QueueClient queueClient;

    public ApiResponse<Shop> createShop(Shop shop) {
        if (shop.getOwner() == null || shop.getOwner().getId() == null) {
            throw new IllegalArgumentException("Owner ID is required.");
        }

        boolean ownerExists = shopOwnerRepository.existsById(shop.getOwner().getId());
        if (!ownerExists) {
            throw new IllegalArgumentException("Shop owner not found.");
        }

        List<Shop> shopList=shopRepository.searchByNameOrAddress(shop.getShopName());
        if(!shopList.isEmpty())
        {
            throw new IllegalArgumentException("Shop with this name already exist");
        }

        Shop saved = shopRepository.save(shop);
        QueueRequest queueRequest = new QueueRequest(saved.getShopId(), saved.getShopName() + " Queue");

        try {
            // Call Queue microservice
            var response = queueClient.createQueue(queueRequest);

            if (!response.isSuccess()) {
                throw new RuntimeException("Queue creation failed: " + response.getMessage());
            }

        } catch (Exception e) {
            // Rollback shop creation if queue creation fails
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to create queue. Rolling back shop creation.", e);
        }
        return new ApiResponse<>(true, "Shop created", saved);
    }

    public ApiResponse<List<ShopDTO>> getAllShops() {
        List<Shop> shops = shopRepository.findAll();
        List<ShopDTO> shopList = shops.stream()
                .map(ShopMapper::toDTO)
                .toList();

        return new ApiResponse<>(true, "All shops", shopList);
    }

    public ApiResponse<ShopDTO> updateShop(Long id, Shop updated) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found."));

        shop.setShopName(updated.getShopName());
        shop.setShopAddress(updated.getShopAddress());
        shop.setLatitude(updated.getLatitude());
        shop.setLongitude(updated.getLongitude());
        shop.setStatus(updated.getStatus());
        shop.setPictureUrl(updated.getPictureUrl());
        shop.setUpdatedAt(LocalDateTime.now());
        Shop saved = shopRepository.save(shop);
        return new ApiResponse<>(true, "Shop updated", ShopMapper.toDTO(saved));
    }

    public ApiResponse<String> deleteShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found."));

        shopRepository.delete(shop);
        return new ApiResponse<>(true, "Shop deleted", null);
    }

    public ApiResponse<List<ShopDTO>> searchNearbyShops(double lat, double lon, double radiusMeters) {
        List<Shop> shops = shopRepository.findNearbyShops(lat, lon, radiusMeters);
        List<ShopDTO> shopList = shops.stream()
                .map(ShopMapper::toDTO)
                .toList();
        return new ApiResponse<>(true, "Nearby shops", shopList);
    }

    public ApiResponse<List<ShopDTO>> searchShopsByMinRating(double minRating) {
        List<Shop> shops = shopRepository.findByMinAvgRating(minRating);
        List<ShopDTO> shopList = shops.stream()
                .map(ShopMapper::toDTO)
                .toList();
        return new ApiResponse<>(true, "Shops with rating >= " + minRating, shopList);
    }

    public ApiResponse<List<ShopDTO>> searchShopsByNameOrAddress(String query) {
        List<Shop> shops = shopRepository.searchByNameOrAddress(query.trim());
        List<ShopDTO> shopList = shops.stream()
                .map(ShopMapper::toDTO)
                .toList();
        return new ApiResponse<>(true, "Search results for query", shopList);
    }
}
