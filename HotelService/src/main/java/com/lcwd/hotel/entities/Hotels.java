package com.lcwd.hotel.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hotel_id")
    private int id;

    @Column(name = "hotel_name", length = 15)
    @NotBlank(message = "Hotel Name is required")
    private String hotelName;

    @NotBlank(message = "Hotel Location is required")
    private String hotelLocation;

    private String hotelAbout;
}
