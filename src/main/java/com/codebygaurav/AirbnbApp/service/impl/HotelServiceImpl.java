package com.codebygaurav.AirbnbApp.service.impl;

import com.codebygaurav.AirbnbApp.dto.HotelDto;
import com.codebygaurav.AirbnbApp.entity.Hotel;
import com.codebygaurav.AirbnbApp.entity.Room;
import com.codebygaurav.AirbnbApp.exception.ResourceNotFoundException;
import com.codebygaurav.AirbnbApp.repository.HotelRepository;
import com.codebygaurav.AirbnbApp.service.HotelService;
import com.codebygaurav.AirbnbApp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService  inventoryService;

    public HotelDto createNewHotel(HotelDto hotelDto){
        log.info("creating a new hotel with name:{}",hotelDto.getName());
    Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
    hotel.setActive(false);
    hotel = hotelRepository.save(hotel);
        log.info("created a new hotel with name:{}",hotelDto.getId());
    return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting hotel with ID:{}",id);
        Hotel hotel = hotelRepository
                    .findById(id)
                    .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID:"+id));
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating hotel with ID:{}",id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID:"+id));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID:"+id));
        hotelRepository.deleteById(id);
        //TODO: delete the future inventories for this hotel
        for(Room room : hotel.getRooms()){
            inventoryService.deleteFutureInventories(room);
        }    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID:{}",hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID:"+hotelId));
        hotel.setActive(true);
        //TODO: Create inventory for all the rooms for the hotel
        //Assuming only do it once
        for(Room room : hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }

    }

}
