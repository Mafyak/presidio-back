package com.presidio.imdbtask.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ActorDto extends ActorShortDto{

    private Set<MovieShortDto> movies;
}
