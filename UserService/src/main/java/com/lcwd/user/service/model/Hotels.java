package com.lcwd.user.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotels {

    private int id;
    private String hotelName;
    private String hotelLocation;
    private String hotelAbout;
}
