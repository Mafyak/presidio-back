package com.presidio.imdbtask.dto;

import com.presidio.imdbtask.entity.Language;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class MovieDtoMini {

    private Long id;
    private String title;
    private String plot;
    private LocalDate releaseDate;
    private Language lang;
    private String image;
    private Double rating;
    private Set<GenreDto> genre;
}
