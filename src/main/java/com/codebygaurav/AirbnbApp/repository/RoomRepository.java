package com.codebygaurav.AirbnbApp.repository;

import com.codebygaurav.AirbnbApp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
