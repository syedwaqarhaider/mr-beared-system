package com.beared.shopservice.mapper;

import com.beared.shopservice.dto.Shop2ServiceDTO;
import com.beared.shopservice.model.Shop2Service;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Shop2ServiceMapper {

    public Shop2ServiceDTO toDTO(Shop2Service s) {
        return Shop2ServiceDTO.builder()
                .id(s.getId())
                .shopId(s.getShop().getShopId())
                .serviceId(s.getServices().getServiceId())
                .serviceName(s.getServices().getServiceName())
                .serviceDescription(s.getServices().getDescription())
                .price(s.getPrice())
                .durationMinutes(s.getDurationMinutes())
                .active(s.getActive())
                .build();
    }

    public List<Shop2ServiceDTO> toDTOList(List<Shop2Service> entities) {
        return entities.stream().map(this::toDTO).toList();
    }
}
