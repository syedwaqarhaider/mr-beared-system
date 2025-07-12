package com.beared.shopservice.service;

import com.beared.shopservice.model.ShopOwner;
import com.beared.shopservice.repository.ShopOwnerRepository;
import com.beared.shopservice.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopOwnerService {

    private final ShopOwnerRepository shopOwnerRepository;

    public ApiResponse<ShopOwner> createShopOwner(ShopOwner owner) {
        if (shopOwnerRepository.existsByEmail(owner.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }
        if (shopOwnerRepository.existsByPhoneNumber(owner.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists.");
        }
        if (shopOwnerRepository.existsByCnic(owner.getCnic())) {
            throw new RuntimeException("CNIC already exists.");
        }
        return new ApiResponse<>(true, "Shop owner created", shopOwnerRepository.save(owner));
    }

    public ApiResponse<List<ShopOwner>> getAllOwners() {
        return new ApiResponse<>(true, "All shop owners", shopOwnerRepository.findAll());
    }

    public ApiResponse<ShopOwner> updateOwner(Long id, ShopOwner updated) {
        ShopOwner owner = shopOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found."));

        if (!owner.getEmail().equals(updated.getEmail())
                && shopOwnerRepository.existsByEmail(updated.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        if (!owner.getPhoneNumber().equals(updated.getPhoneNumber())
                && shopOwnerRepository.existsByPhoneNumber(updated.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists.");
        }

        if (!owner.getCnic().equals(updated.getCnic())
                && shopOwnerRepository.existsByCnic(updated.getCnic())) {
            throw new RuntimeException("CNIC already exists.");
        }

        owner.setFullName(updated.getFullName());
        owner.setEmail(updated.getEmail());
        owner.setPhoneNumber(updated.getPhoneNumber());
        owner.setCnic(updated.getCnic());
        owner.setPasswordHash(updated.getPasswordHash());

        return new ApiResponse<>(true, "Owner updated", shopOwnerRepository.save(owner));
    }

    public ApiResponse<String> deleteOwner(Long id) {
        ShopOwner owner = shopOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found."));

        shopOwnerRepository.delete(owner);
        return new ApiResponse<>(true, "Owner deleted", null);
    }
}
