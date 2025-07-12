package com.beared.shopservice.service;

import com.beared.shopservice.model.BarberServices;
import com.beared.shopservice.repository.BarberServicesRepository;
import com.beared.shopservice.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BarberServicesService {

    private final BarberServicesRepository barberServicesRepository;

    public ApiResponse<BarberServices> createService(BarberServices service) {
        if (barberServicesRepository.existsByServiceName(service.getServiceName())) {
            throw new RuntimeException("Service name already exists.");
        }
        return new ApiResponse<>(true, "Service created", barberServicesRepository.save(service));
    }

    public ApiResponse<List<BarberServices>> getAllServices() {
        return new ApiResponse<>(true, "All services", barberServicesRepository.findAll());
    }

    public ApiResponse<BarberServices> updateService(Long id, BarberServices updated) {
        BarberServices service = barberServicesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found."));

        if (!service.getServiceName().equals(updated.getServiceName()) &&
                barberServicesRepository.existsByServiceName(updated.getServiceName())) {
            throw new RuntimeException("Service name already exists.");
        }

        service.setServiceName(updated.getServiceName());
        service.setDescription(updated.getDescription());

        return new ApiResponse<>(true, "Service updated", barberServicesRepository.save(service));
    }

    public ApiResponse<String> deleteService(Long id) {
        BarberServices service = barberServicesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found."));

        barberServicesRepository.delete(service);
        return new ApiResponse<>(true, "Service deleted", null);
    }
}
