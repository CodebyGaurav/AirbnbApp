package com.codebygaurav.AirbnbApp.service;

import com.codebygaurav.AirbnbApp.dto.HotelDto;
import com.codebygaurav.AirbnbApp.dto.HotelInfoDto;
import com.codebygaurav.AirbnbApp.entity.Hotel;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long id);
    HotelDto updateHotelById(Long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel( Long hotelId);

    HotelInfoDto getHotelInfoById(Long hotelId);
}
