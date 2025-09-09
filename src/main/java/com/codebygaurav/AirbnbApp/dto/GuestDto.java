package com.codebygaurav.AirbnbApp.dto;

import com.codebygaurav.AirbnbApp.entity.User;
import com.codebygaurav.AirbnbApp.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {

    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;

}
