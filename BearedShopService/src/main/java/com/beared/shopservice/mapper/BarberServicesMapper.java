package com.beared.shopservice.mapper;

import com.beared.shopservice.dto.BarberServicesDTO;
import com.beared.shopservice.model.BarberServices;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BarberServicesMapper {

    public BarberServicesDTO toDTO(BarberServices b) {
        return BarberServicesDTO.builder()
                .serviceId(b.getServiceId())
                .serviceName(b.getServiceName())
                .description(b.getDescription())
                .build();
    }

    public List<BarberServicesDTO> toDTOList(List<BarberServices> list) {
        return list.stream().map(this::toDTO).toList();
    }
}
