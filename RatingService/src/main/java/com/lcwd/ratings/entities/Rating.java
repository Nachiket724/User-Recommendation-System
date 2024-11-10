package com.lcwd.ratings.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ratingId;

    @Column(name = "user_id")
    @Positive(message = "User ID must be positive")
    private int userId;

    @Column(name = "hotel_id")
    @Positive(message = "Hotel ID must be positive")
    private int hotelId;

    @Column(name = "rating")
    @Min(value = 0, message = "Rating must be between 0 and 10")
    private int rating;
    
    private String feedback;
}
