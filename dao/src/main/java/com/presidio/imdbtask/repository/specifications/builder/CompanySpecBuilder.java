package com.presidio.imdbtask.repository.specifications.builder;

import com.presidio.imdbtask.entity.Movie;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public interface CompanySpecBuilder {

    Specification<Movie> build(String title, LocalDate releasedBeforeDate, LocalDate releasedAfterDate, String genre, Long actorId);
}
