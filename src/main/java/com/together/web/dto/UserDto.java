package com.together.web.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

    private int id;

    private String username;

    private String name;

}