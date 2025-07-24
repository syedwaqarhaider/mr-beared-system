package com.beared.shopservice.controller;


import com.beared.shopservice.model.Shop2Service;
import com.beared.shopservice.response.ApiResponse;
import com.beared.shopservice.service.Shop2ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop2service")
@RequiredArgsConstructor
public class Shop2ServiceController {

    private final Shop2ServiceService service;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<?>> addServiceToShop(
            @RequestParam Long shopId,
            @RequestParam Long serviceId,
            @RequestParam Double price,
            @RequestParam Integer durationMinutes) {
        return ResponseEntity.ok(service.addServiceToShop(shopId, serviceId, price, durationMinutes));
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ApiResponse<?>> getAllShopServices(@PathVariable Long shopId) {
        return ResponseEntity.ok(service.getAllShopServices(shopId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateShopService(
            @PathVariable Long id,
            @RequestParam Double price,
            @RequestParam Integer durationMinutes,
            @RequestParam Boolean active) {
        return ResponseEntity.ok(service.updateShopService(id, price, durationMinutes, active));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteShopService(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteShopService(id));
    }
}
