package com.lcwd.user.service.services.implementation;

import com.lcwd.user.service.entities.Users;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.external.services.RatingService;
import com.lcwd.user.service.model.Hotels;
import com.lcwd.user.service.model.Rating;
import com.lcwd.user.service.repository.UserRepository;
import com.lcwd.user.service.services.UserService;
import com.lcwd.user.service.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${URL.GET.RATINGS.BY.USERID}")
    private String urlToGetRatingsByUserId;
    @Value("${URL.GET.HOTELS.BY.HOTELID}")
    private String urlToGetHotelsByHotelId;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Users saveUser(Users user) {

        Users newlyCreatedUser = userRepository.save(user);
        Rating rating = user.getRatings().get(0);
        rating.setUserId(newlyCreatedUser.getUserId());
        ResponseEntity<Rating> callingRatingService = ratingService.createRating(rating);
        newlyCreatedUser.setRatings(Arrays.asList(callingRatingService.getBody()));
        return newlyCreatedUser;
    }

    @Override
    public List<Users> getAllUser() {
        List<Users> usersList = userRepository.findAll();

        usersList.forEach(users -> {
            ResponseEntity<List<Rating>> ratingsPerUserId = ratingService.getRatingsByUserId(users.getUserId());
            ratingsPerUserId.getBody().forEach(rating -> {
                Hotels hotelsPerRatingId = hotelService.getHotels(rating.getHotelId());
                rating.setHotels(hotelsPerRatingId);
            });

            users.setRatings(ratingsPerUserId.getBody());

        });
        return usersList;
    }

    @Cacheable(value = "userCache", key = "#userId")
    @Override
    public Users getUser(int userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND+" "+userId));


        ResponseEntity<List<Rating>> response = restTemplate.exchange(urlToGetRatingsByUserId+userId, HttpMethod.GET,null,new ParameterizedTypeReference<List<Rating>>() {});
        //List<Rating> ratings = restTemplate.getForObject(urlToGetRatingsByUserId+userId, List.class);
        List<Rating> ratings = response.getBody();

        ratings.stream().forEach(rating -> {

            //TODO: HERE WE ARE COMMUNICATING TWO MICROSERVICES USING RESTTEMPLATE
            /*
            ResponseEntity<Hotels> forEntity = restTemplate.getForEntity(urlToGetHotelsByHotelId + rating.getHotelId(), Hotels.class);
            rating.setHotels(forEntity.getBody());
             */

            //TODO: NOW WE HAVE COMMENTED THE RESTTEMPLATE LOGIC AND WE ARE USING Fiegn Client

            Hotels hotels = hotelService.getHotels(rating.getHotelId());
            rating.setHotels(hotels);
        });
        user.setRatings(ratings);
        return user;
    }

    @CacheEvict(value = "userCache", key = "#userId")
    @Override
    @Transactional
    public void deleteUser(int userId) {
        Users users = getUser(userId);
        if(users!=null){
            userRepository.deleteById(userId);
        }
    }

    @CacheEvict(value = "userCache", key = "#userId")
    @Override
    @Transactional
    public Users updateUser(int userId, Users user) {
        Users existingUser = getUser(userId);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAbout(user.getAbout());
        existingUser.setRatings(user.getRatings());
        return userRepository.save(existingUser);
    }

    @Cacheable(value = "allUserCache", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    @Override
    public Page<Users> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
