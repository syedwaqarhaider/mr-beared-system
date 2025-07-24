package com.beared.shopservice.controller;

import com.beared.shopservice.model.ShopOwner;
import com.beared.shopservice.response.ApiResponse;
import com.beared.shopservice.service.ShopOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop-owners")
@RequiredArgsConstructor
public class ShopOwnerController {

    private final ShopOwnerService shopOwnerService;

    @PostMapping
    public ResponseEntity<ApiResponse<ShopOwner>> create(@RequestBody ShopOwner owner) {
        return ResponseEntity.ok(shopOwnerService.createShopOwner(owner));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll() {
        return ResponseEntity.ok(shopOwnerService.getAllOwners());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ShopOwner>> update(@PathVariable Long id, @RequestBody ShopOwner owner) {
        return ResponseEntity.ok(shopOwnerService.updateOwner(id, owner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(shopOwnerService.deleteOwner(id));
    }
}