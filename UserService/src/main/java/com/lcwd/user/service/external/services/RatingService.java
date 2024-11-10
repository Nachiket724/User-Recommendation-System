package com.lcwd.user.service.external.services;

import com.lcwd.user.service.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("RATING-SERVICE")
public interface RatingService {

    @PostMapping("/ratings")
    ResponseEntity<Rating> createRating(Rating rating);

    @GetMapping("/ratings/users/{userId}")
    ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable int userId);

    @PutMapping("/ratings/{ratingId}")
    Rating updateRating(@PathVariable int ratingId, Rating rating);

    @DeleteMapping("/ratings/{ratingId}")
    Rating deleteRating(@PathVariable int ratingId);
}
