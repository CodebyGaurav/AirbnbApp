package com.codebygaurav.AirbnbApp.service;

import com.codebygaurav.AirbnbApp.dto.BookingDto;
import com.codebygaurav.AirbnbApp.dto.BookingRequest;
import com.codebygaurav.AirbnbApp.dto.GuestDto;

import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
