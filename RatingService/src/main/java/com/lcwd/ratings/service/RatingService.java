package com.lcwd.ratings.service;

import com.lcwd.ratings.dto.RatingSummary;
import com.lcwd.ratings.entities.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingService {

    Rating createRating(Rating rating);

    List<Rating> getAllRating();
    Page<Rating> getAllRating(Pageable pageable);

    List<Rating> getAllRatingsByUserId(int userId);
    List<Rating> getAllRatingsByHotelId(int hotelId);
    List<Rating> getAllRatingsByUserIdAndHotelId(int userId, int hotelId);
    List<Rating> getAllRatingsByUserIdOrHotelId(int userId, int hotelId);
    List<Rating> getAllRatingsByUserIdAndHotelIdAndRatingId(int userId, int hotelId, int ratingId);
    List<Rating> getDistinctByUserId(int userId);
    List<Rating> getAllRatingsByUserId(List<Integer> userId);
    //List<Rating> findHighRatingsByUser(int userId, int minRating);
    List<Rating> findTop2RatingsByUserId(int userId);
    List<Rating> getAllRatingsByRatingsDesc();

    //List<RatingSummary> getAverageRatingByUser();

    void deleteAllRatingsOfUser(int userId);
    void deleteAllRatingsOfHotel(int hotelId);

    int updateRatingForUser(int userId, int ratingId, int rating);

    int countByUserId(int userId);

    boolean existsByUserIdAndHotelId(int userId, int hotelId);

}
