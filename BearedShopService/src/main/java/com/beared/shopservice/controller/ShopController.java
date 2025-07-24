package com.beared.shopservice.controller;

import com.beared.shopservice.dto.ShopDTO;
import com.beared.shopservice.model.Shop;
import com.beared.shopservice.response.ApiResponse;
import com.beared.shopservice.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    public ResponseEntity<ApiResponse<Shop>> create(@RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.createShop(shop));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll() {
        return ResponseEntity.ok(shopService.getAllShops());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Long id, @RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.updateShop(id, shop));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(shopService.deleteShop(id));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<?>> searchNearbyShops(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam double radius) {
        return ResponseEntity.ok(shopService.searchNearbyShops(lat, lon, radius));
    }

    @GetMapping("/search-by-rating")
    public ResponseEntity<ApiResponse<?>> searchByMinRating(
            @RequestParam double minRating) {
        return ResponseEntity.ok(shopService.searchShopsByMinRating(minRating));
    }

    @GetMapping("/search-by-text")
    public ResponseEntity<ApiResponse<?>> searchByNameOrAddress(
            @RequestParam String query) {
        return ResponseEntity.ok(shopService.searchShopsByNameOrAddress(query));
    }

}