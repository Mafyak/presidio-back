package com.presidio.imdbtask.dto;

import com.presidio.imdbtask.entity.UserType;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private UserType type;
    private String fullName;
    private String avatar; //url to avatar
}
