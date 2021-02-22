package com.presidio.imdbtask.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class ReviewDto {

    private Long id;
    private Integer rating;
    private String summary;
    private LocalDate creationDate;
    private UserDto user;
    private Set<Long> likes; // ids of users that liked this review
}
