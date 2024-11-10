package com.lcwd.ratings.controller;

import com.lcwd.ratings.entities.Rating;
import com.lcwd.ratings.service.RatingService;
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
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(@Valid @RequestBody Rating rating){
        if(rating==null || rating.getFeedback()==null){
            throw new IllegalArgumentException("REQUIRED FIELD ARE NEEDED!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.createRating(rating));
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings(){
        return ResponseEntity.ok(ratingService.getAllRating());
    }

    @GetMapping("/page")
    public ResponseEntity<List<Rating>> getAllRating(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Rating> ratings = ratingService.getAllRating(pageable);
        return ResponseEntity.ok(ratings.getContent());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable int userId){
        return ResponseEntity.ok(ratingService.getAllRatingsByUserId(userId));
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable int hotelId){
        return ResponseEntity.ok(ratingService.getAllRatingsByHotelId(hotelId));
    }

    @GetMapping("/userIdAndHotelId")
    public ResponseEntity<List<Rating>> getRatingsByUserAndHotelId(@RequestParam int userId, @RequestParam int hotelId){
        return ResponseEntity.ok(ratingService.getAllRatingsByUserIdAndHotelId(userId,hotelId));
    }

    @GetMapping("/userIdOrHotelId")
    public ResponseEntity<List<Rating>> getRatingsByUserOrHotelId(@RequestParam int userId, @RequestParam int hotelId){
        return ResponseEntity.ok(ratingService.getAllRatingsByUserIdOrHotelId(userId,hotelId));
    }

    @GetMapping("/userIdAndHotelIdAndRatingId")
    public ResponseEntity<List<Rating>> getRatingsByUserAndHotelIdAndRatingId(@RequestParam int userId, @RequestParam int hotelId, @RequestParam int ratingId){
        return ResponseEntity.ok(ratingService.getAllRatingsByUserIdAndHotelIdAndRatingId(userId,hotelId, ratingId));
    }

    @GetMapping("/distinct/{userId}")
    public ResponseEntity<List<Rating>> getDistinctRatingsByUserId(@PathVariable int userId){
        return ResponseEntity.ok(ratingService.getDistinctByUserId(userId));
    }

    @GetMapping("/allRatingsByUserId")
    public ResponseEntity<List<Rating>> getAllRatingsByUserIdIn(@RequestParam List<Integer> userIds){
        List<Rating> ratings = ratingService.getAllRatingsByUserId(userIds);
        return ResponseEntity.ok(ratings);
    }
}
