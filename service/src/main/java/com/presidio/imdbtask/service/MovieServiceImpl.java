package com.presidio.imdbtask.service;

import com.presidio.imdbtask.entity.Movie;
import com.presidio.imdbtask.repository.MovieRepository;
import com.presidio.imdbtask.repository.specifications.builder.CompanySpecBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MovieServiceImpl extends CrudServiceImpl<Movie, Long, MovieRepository> implements MovieService {

    private final MovieRepository movieRepository;
    private final CompanySpecBuilder companySpecBuilder;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, CompanySpecBuilder companySpecBuilder) {
        super(movieRepository);
        this.movieRepository = movieRepository;
        this.companySpecBuilder = companySpecBuilder;
    }

    @Override
    public Iterable<Movie> findAll(Sort sort) {
        return movieRepository.findAll(sort);
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Page<Movie> find(String title, LocalDate releasedBeforeDate, LocalDate releasedAfterDate, String genre, Long actorId, Pageable pageable) {
        Specification<Movie> spec = companySpecBuilder.build(title, releasedBeforeDate, releasedAfterDate, genre, actorId);
        return movieRepository.findAll(spec, pageable);
    }
}
