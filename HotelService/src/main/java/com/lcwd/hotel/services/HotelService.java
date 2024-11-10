package com.lcwd.hotel.services;

import com.lcwd.hotel.entities.Hotels;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {

    Hotels createHotel(Hotels hotels);

    List<Hotels> getAllHotels();

    Hotels getHotelById(int hotelId);

    Hotels updateHotelById(int hotelId, Hotels hotels);

    void deleteHotelById(int hotelId);

    Page<Hotels> getAllHotels(Pageable pageable);
}
