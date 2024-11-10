package com.lcwd.ratings.repository;

import com.lcwd.ratings.dto.RatingSummary;
import com.lcwd.ratings.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    List<Rating> findByUserId(int userId);
    List<Rating> findByHotelId(int hotelId);
    List<Rating> findByUserIdAndHotelId(int userId, int hotelId);
    List<Rating> findByUserIdOrHotelId(int userId, int hotelId);
    List<Rating> findByUserIdAndHotelIdAndRatingId(int userId, int hotelId, int ratingId);
    List<Rating> findDistinctByUserId(int userId);
    List<Rating> findByUserIdIn(List<Integer> userId);

    //@Query("SELECT r FROM Rating r WHERE r.userId = :userId AND r.rating > :minRating")
    //List<Rating> findHighRatingsByUserId(@Param("userId") int userId, @Param("minRating") int minRating);

    @Query(value = "SELECT * FROM Rating r WHERE r.userId = :userId ORDER BY r.rating DESC LIMIT 2", nativeQuery = true)
    List<Rating> findTop2RatingsByUserId(int userId);

    List<Rating> findAllByOrderByRatingDesc();

    /*
    @Query("SELECT r.userId as userId, AVG(r.rating) as averageRating FROM Rating r GROUP BY r.userId")
    List<RatingSummary> findAverageRatingsByUser();
    */


    void deleteByUserId(int userId);
    void deleteByHotelId(int hotelId);

    @Modifying
    @Query(value = "UPDATE user_ratings r SET r.rating = :newRating WHERE r.user_id = :userId and r.hotel_id = :hotelId", nativeQuery = true)
    int updateRatingForUser(@Param("userId") int userId,@Param("hotelId") int hotelId,@Param("newRating") int newRating);

    int countByUserId(int userId);

    boolean existsByUserIdAndHotelId(int userId, int hotelId);
}
