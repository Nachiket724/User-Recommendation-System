package com.lcwd.hotel.services.implementation;

import com.lcwd.hotel.entities.Hotels;
import com.lcwd.hotel.exceptions.ResourceNotFoundException;
import com.lcwd.hotel.respository.HotelRespository;
import com.lcwd.hotel.services.HotelService;
import com.lcwd.hotel.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRespository hotelRespository;

    @Override
    public Hotels createHotel(Hotels hotels) {
        return hotelRespository.save(hotels);
    }

    @Override
    public List<Hotels> getAllHotels() {
        return hotelRespository.findAll();
    }

    @Cacheable(value = "hotelCache", key = "#hotelId")
    @Override
    public Hotels getHotelById(int hotelId) {
        return hotelRespository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException(Constants.HOTEL_NOT_FOUND+" "+hotelId));
    }

    @CacheEvict(value = "hotelCache", key = "#hotelId")
    @Override
    @Transactional
    public Hotels updateHotelById(int hotelId, Hotels hotels) {
        Hotels existingHotel = getHotelById(hotelId);
        existingHotel.setHotelName(hotels.getHotelName());
        existingHotel.setHotelLocation(hotels.getHotelLocation());
        existingHotel.setHotelAbout(hotels.getHotelAbout());
        return hotelRespository.save(existingHotel);
    }

    @CacheEvict(value = "hotelCache", key = "#hotelId")
    @Override
    @Transactional
    public void deleteHotelById(int hotelId) {
        Hotels existingHotel = getHotelById(hotelId);
        if(existingHotel!=null){
            hotelRespository.deleteById(hotelId);
        }
    }

    @Cacheable(value = "allHotelsCache", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    @Override
    public Page<Hotels> getAllHotels(Pageable pageable) {
        return hotelRespository.findAll(pageable);
    }
}
