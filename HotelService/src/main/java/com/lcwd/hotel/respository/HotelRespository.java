package com.lcwd.hotel.respository;

import com.lcwd.hotel.entities.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRespository extends JpaRepository<Hotels, Integer> {
}
