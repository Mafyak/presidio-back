package com.presidio.imdbtask.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieShortDto {

    private Long id;
    private String title;
    private String image;
    private LocalDate releaseDate;
}
