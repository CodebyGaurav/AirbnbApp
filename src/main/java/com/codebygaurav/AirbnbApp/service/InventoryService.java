package com.codebygaurav.AirbnbApp.service;

import com.codebygaurav.AirbnbApp.dto.HotelDto;
import com.codebygaurav.AirbnbApp.dto.HotelPriceDto;
import com.codebygaurav.AirbnbApp.dto.HotelSearchRequest;
import com.codebygaurav.AirbnbApp.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
