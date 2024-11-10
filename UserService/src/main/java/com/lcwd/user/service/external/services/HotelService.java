package com.lcwd.user.service.external.services;

import com.lcwd.user.service.model.Hotels;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("/hotels/{hotelId}")
    Hotels getHotels(@PathVariable int hotelId);
}
