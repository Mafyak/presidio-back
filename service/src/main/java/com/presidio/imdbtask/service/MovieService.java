package com.presidio.imdbtask.service;

import com.presidio.imdbtask.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

public interface MovieService extends CrudService<Movie, Long> {

    Iterable<Movie> findAll(Sort sort);

    Page<Movie> findAll(Pageable pageable);

    Page<Movie> find(String title, LocalDate releasedBeforeDate, LocalDate releasedAfterDate, String genre, Long actorId, Pageable pageable);
}
