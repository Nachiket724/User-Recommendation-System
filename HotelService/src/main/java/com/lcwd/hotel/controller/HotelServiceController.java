package com.lcwd.hotel.controller;

import com.lcwd.hotel.entities.Hotels;
import com.lcwd.hotel.payload.ApiResponse;
import com.lcwd.hotel.services.HotelService;
import com.lcwd.hotel.utils.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelServiceController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotels> createHotel(@Valid @RequestBody Hotels hotels){
        if(hotels==null || hotels.getHotelName()==null || hotels.getHotelLocation()==null || hotels.getHotelAbout()==null){
            throw new IllegalArgumentException(Constants.INVALID_ARGUMENTS_EXCEPTION);
        }
        Hotels createdHotel = hotelService.createHotel(hotels);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @GetMapping
    public ResponseEntity<List<Hotels>> getAllHotels(){
        List<Hotels> hotels = hotelService.getAllHotels();
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotels> getHotelById(@PathVariable(name = "hotelId") int hotelId){
        Hotels existingHotel = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(existingHotel);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<Hotels> updateHotelById(@PathVariable(name = "hotelId") int hotelId, @Valid @RequestBody Hotels hotels){

        if(hotels==null || hotels.getHotelName()==null || hotels.getHotelLocation()==null || hotels.getHotelAbout()==null){
            throw new IllegalArgumentException(Constants.INVALID_ARGUMENTS_EXCEPTION);
        }

        Hotels existingHotel = hotelService.updateHotelById(hotelId, hotels);
        return ResponseEntity.status(HttpStatus.CREATED).body(existingHotel);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<ApiResponse> deleteHotelById(@PathVariable(name = "hotelId") int hotelId){
        hotelService.deleteHotelById(hotelId);
        ApiResponse response = ApiResponse.builder().message(Constants.HOTEL_DELETED+hotelId).success(true).httpStatus(HttpStatus.ACCEPTED).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Hotels>> getAllHotelsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page,size);
        Page<Hotels> pagedUsers = hotelService.getAllHotels(pageable);
        return ResponseEntity.ok(pagedUsers);
    }

}
