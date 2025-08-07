package com.beared.queueservice.clients;

import com.beared.queueservice.dto.ServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "BearedShopService", url = "${shop.service.url}")
public interface ShopClient {
    @GetMapping("/api/barber-services/bulk")
    List<ServiceDTO> getServicesByIds(@RequestBody List<Long> serviceIds);
}
