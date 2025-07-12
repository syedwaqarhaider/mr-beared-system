package com.beared.shopservice.service;

import com.beared.shopservice.model.BarberServices;
import com.beared.shopservice.model.Shop;
import com.beared.shopservice.model.Shop2Service;
import com.beared.shopservice.repository.BarberServicesRepository;
import com.beared.shopservice.repository.Shop2ServiceRepository;
import com.beared.shopservice.repository.ShopRepository;
import com.beared.shopservice.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class Shop2ServiceService {

    private final Shop2ServiceRepository shop2ServiceRepository;
    private final ShopRepository shopRepository;
    private final BarberServicesRepository barberServicesRepository;

    public ApiResponse<Shop2Service> addServiceToShop(Long shopId, Long serviceId, Double price, Integer durationMinutes) {
        if (price == null || price <= 0) {
            throw new RuntimeException("Price must be positive.");
        }
        if (durationMinutes == null || durationMinutes <= 0) {
            throw new RuntimeException("Duration must be positive.");
        }

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found."));

        BarberServices service = barberServicesRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Barber Service not found."));

        shop2ServiceRepository.findByShop_ShopIdAndServices_ServiceId(shopId, serviceId)
                .ifPresent(existing -> {
                    throw new RuntimeException("This service is already added to the shop.");
                });

        Shop2Service shop2Service = Shop2Service.builder()
                .shop(shop)
                .services(service)
                .price(price)
                .durationMinutes(durationMinutes)
                .active(true)
                .build();

        return new ApiResponse<>(true, "Service added to shop", shop2ServiceRepository.save(shop2Service));
    }

    public ApiResponse<List<Shop2Service>> getAllShopServices(Long shopId) {
        if (!shopRepository.existsById(shopId)) {
            throw new RuntimeException("Shop not found.");
        }

        List<Shop2Service> services = shop2ServiceRepository.findByShop_ShopId(shopId);

        return new ApiResponse<>(true, "Services retrieved", services);
    }

    public ApiResponse<Shop2Service> updateShopService(Long id, Double price, Integer durationMinutes, Boolean active) {
        Shop2Service shop2Service = shop2ServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop2Service not found."));

        if (price == null || price <= 0) {
            throw new RuntimeException("Price must be positive.");
        }

        if (durationMinutes == null || durationMinutes <= 0) {
            throw new RuntimeException("Duration must be positive.");
        }

        if (active == null) {
            throw new RuntimeException("Active flag must not be null.");
        }

        shop2Service.setPrice(price);
        shop2Service.setDurationMinutes(durationMinutes);
        shop2Service.setActive(active);

        return new ApiResponse<>(true, "Shop2Service updated", shop2ServiceRepository.save(shop2Service));
    }

    public ApiResponse<String> deleteShopService(Long id) {
        Shop2Service shop2Service = shop2ServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop2Service not found."));

        shop2ServiceRepository.delete(shop2Service);
        return new ApiResponse<>(true, "Shop2Service deleted.", null);
    }
}
