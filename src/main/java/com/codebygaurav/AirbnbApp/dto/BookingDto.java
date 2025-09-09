package com.codebygaurav.AirbnbApp.dto;

import com.codebygaurav.AirbnbApp.entity.Guest;
import com.codebygaurav.AirbnbApp.entity.Hotel;
import com.codebygaurav.AirbnbApp.entity.Room;
import com.codebygaurav.AirbnbApp.entity.User;
import com.codebygaurav.AirbnbApp.entity.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private BookingStatus bookingStatus;
    private Set<Guest> guests;

}
