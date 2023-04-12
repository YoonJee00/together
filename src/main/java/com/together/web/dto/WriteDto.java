package com.together.web.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WriteDto {

    public int imageId;

    public String caption;
}
