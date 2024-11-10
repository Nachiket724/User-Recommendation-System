package com.lcwd.user.service.controller;

import com.lcwd.user.service.entities.Users;
import com.lcwd.user.service.payload.ApiResponse;
import com.lcwd.user.service.services.UserService;
import com.lcwd.user.service.utils.Constants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //CREATE
    @PostMapping
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users users){
        if(users==null || users.getName()==null|| users.getEmail()==null || users.getAbout()==null){
            throw new IllegalArgumentException(Constants.INVALID_ARGUMENTS_EXCEPTION);
        }
        Users usersResponse = userService.saveUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(usersResponse);
    }

    int retryCount = 1;
    //SINGLE USER GET
    @GetMapping("/{userId}")
    //@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    //@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<Users> getSingleUser(@PathVariable(name = "userId") int userId){
        System.out.println("Retry Count: "+retryCount);
        retryCount++;
        Users user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //ALL USER GET
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId){
        userService.deleteUser(userId);
        ApiResponse apiResponse=ApiResponse.builder().message("USER WITH ID: "+userId+" DELETED SUCCESSFULLY!").httpStatus(HttpStatus.OK).success(true).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Users> updateUser(@PathVariable int userId, @Valid @RequestBody Users users){
        Users updateUser = userService.updateUser(userId,users);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Users>> getUsersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Users> pagedUsers = userService.getAllUser(pageable);
        return ResponseEntity.ok(pagedUsers);
    }

    //CREATING FALLBACK METHOD OF CIRCUITBEAKER

    public ResponseEntity<Users> ratingHotelFallback(int userId, Exception ex){
        System.out.println("Fallback is executed because service is down: "+ex.getMessage());

        Users user = Users.builder()
                .email("dummmy@gmail.com")
                .name("DUMMY")
                .about("THIS USER IS CREATED DUMMY BECAUSE SOME SERVICES ARE DOWN")
                .userId(-1)
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
