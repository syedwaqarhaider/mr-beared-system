package com.beared.shopservice.controller;

import com.beared.shopservice.dto.BarberServicesDTO;
import com.beared.shopservice.model.BarberServices;
import com.beared.shopservice.response.ApiResponse;
import com.beared.shopservice.service.BarberServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barber-services")
@RequiredArgsConstructor
public class BarberServicesController {

    private final BarberServicesService barberServicesService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody BarberServices service) {
        return ResponseEntity.ok(barberServicesService.createService(service));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll() {
        return ResponseEntity.ok(barberServicesService.getAllServices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Long id, @RequestBody BarberServices service) {
        return ResponseEntity.ok(barberServicesService.updateService(id, service));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(barberServicesService.deleteService(id));
    }

    @GetMapping("/bulk")
    public ResponseEntity<List<BarberServicesDTO>> getBulkServices(@RequestBody List<Long> serviceIds)
    {
        return ResponseEntity.ok(barberServicesService.findBulkServices(serviceIds));
    }

}