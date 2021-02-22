package com.presidio.imdbtask.dto;

import lombok.Data;

import java.util.Set;

@Data
public class MovieDto extends MovieDtoMini {

    private Set<ActorShortDto> cast;
    private Set<ReviewDto> reviews;
}
