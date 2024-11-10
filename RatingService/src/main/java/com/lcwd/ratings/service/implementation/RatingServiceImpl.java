package com.lcwd.ratings.service.implementation;

import com.lcwd.ratings.dto.RatingSummary;
import com.lcwd.ratings.entities.Rating;
import com.lcwd.ratings.repository.RatingRepository;
import com.lcwd.ratings.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    @Override
    public Page<Rating> getAllRating(Pageable pageable) {
        return ratingRepository.findAll(pageable);
    }

    @Override
    public List<Rating> getAllRatingsByUserId(int userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getAllRatingsByHotelId(int hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }

    @Override
    public List<Rating> getAllRatingsByUserIdAndHotelId(int userId, int hotelId) {
        return ratingRepository.findByUserIdAndHotelId(userId, hotelId);
    }

    @Override
    public List<Rating> getAllRatingsByUserIdOrHotelId(int userId, int hotelId) {
        return ratingRepository.findByUserIdOrHotelId(userId, hotelId);
    }

    @Override
    public List<Rating> getAllRatingsByUserIdAndHotelIdAndRatingId(int userId, int hotelId, int ratingId) {
        return ratingRepository.findByUserIdAndHotelIdAndRatingId(userId, hotelId, ratingId);
    }

    @Override
    public List<Rating> getDistinctByUserId(int userId) {
        return ratingRepository.findDistinctByUserId(userId);
    }
//WILL DO AFTERWARDS TODO
    @Override
    public List<Rating> getAllRatingsByUserId(List<Integer> userId) {
        return ratingRepository.findByUserIdIn(userId);
    }

    /*
    @Override
    public List<Rating> findHighRatingsByUser(int userId, int minRating) {
        return ratingRepository.findHighRatingsByUserId(userId,minRating);
    }
    */
    @Override
    public List<Rating> findTop2RatingsByUserId(int userId) {
        return ratingRepository.findTop2RatingsByUserId(userId);
    }

    @Override
    public List<Rating> getAllRatingsByRatingsDesc() {
        return ratingRepository.findAllByOrderByRatingDesc();
    }

    /*
    @Override
    public List<RatingSummary> getAverageRatingByUser() {
        return ratingRepository.findAverageRatingsByUser();
    }

     */

    @Transactional
    @Override
    public void deleteAllRatingsOfUser(int userId) {
        List<Rating> ratings = getAllRatingsByUserId(userId);
        if(ratings!=null || !ratings.isEmpty()){
            ratingRepository.deleteByUserId(userId);
            return;
        }else{
            throw new ResourceAccessException("NOT RATING FOUND FOR USER ID: "+userId);
        }
    }

    @Transactional
    @Override
    public void deleteAllRatingsOfHotel(int hotelId) {
        List<Rating> ratings = getAllRatingsByHotelId(hotelId);
        if(ratings!=null || !ratings.isEmpty()){
            ratingRepository.deleteByHotelId(hotelId);
            return;
        }else{
            throw new ResourceAccessException("NOT RATING FOUND FOR HOTEL ID: "+hotelId);
        }
    }

    @Transactional
    @Override
    public int updateRatingForUser(int userId, int ratingId, int rating) {
       return ratingRepository.updateRatingForUser(userId,ratingId,rating);
    }


    @Override
    public int countByUserId(int userId) {
        return ratingRepository.countByUserId(userId);
    }

    @Override
    public boolean existsByUserIdAndHotelId(int userId, int hotelId) {
        return ratingRepository.existsByUserIdAndHotelId(userId,hotelId);
    }
}
