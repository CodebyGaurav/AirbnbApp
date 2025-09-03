package com.codebygaurav.AirbnbApp.service;

import com.codebygaurav.AirbnbApp.entity.Room;

public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteFutureInventories(Room room);
}
