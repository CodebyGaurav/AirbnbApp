package com.codebygaurav.AirbnbApp.repository;

import com.codebygaurav.AirbnbApp.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
