package com.codebygaurav.AirbnbApp.service.impl;

import com.codebygaurav.AirbnbApp.dto.HotelDto;
import com.codebygaurav.AirbnbApp.dto.HotelInfoDto;
import com.codebygaurav.AirbnbApp.dto.RoomDto;
import com.codebygaurav.AirbnbApp.entity.Hotel;
import com.codebygaurav.AirbnbApp.entity.Room;
import com.codebygaurav.AirbnbApp.exception.ResourceNotFoundException;
import com.codebygaurav.AirbnbApp.repository.HotelRepository;
import com.codebygaurav.AirbnbApp.repository.RoomRepository;
import com.codebygaurav.AirbnbApp.service.HotelService;
import com.codebygaurav.AirbnbApp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService  inventoryService;
    private final RoomRepository roomRepository;

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

        //TODO: delete the future inventories for this hotel
        for(Room room : hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

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

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID:"+hotelId));

        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map(element -> modelMapper.map(element,RoomDto.class))
                .toList();

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }

}
