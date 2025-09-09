package com.codebygaurav.AirbnbApp.service.impl;

import com.codebygaurav.AirbnbApp.dto.RoomDto;
import com.codebygaurav.AirbnbApp.entity.Hotel;
import com.codebygaurav.AirbnbApp.entity.Room;
import com.codebygaurav.AirbnbApp.exception.ResourceNotFoundException;
import com.codebygaurav.AirbnbApp.repository.HotelRepository;
import com.codebygaurav.AirbnbApp.repository.RoomRepository;
import com.codebygaurav.AirbnbApp.service.InventoryService;
import com.codebygaurav.AirbnbApp.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating a new room in hotel with ID:{}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+hotelId));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        //TODO: create inventory as soon as room is created as if hotel is active
        if(hotel.getActive()){
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all room in hotel with ID:{}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+hotelId));
        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting all room with with ID:{}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+roomId));

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("Delete the room with with ID:{}", roomId);
        boolean exists = roomRepository.existsById(roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+roomId));

        //TODO: delete all future inventory for this room
        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);
    }
}
