package com.lcwd.user.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private int ratingId;
    private int userId;
    private int hotelId;
    private int rating;
    private String feedback;

    private Hotels hotels;
}
