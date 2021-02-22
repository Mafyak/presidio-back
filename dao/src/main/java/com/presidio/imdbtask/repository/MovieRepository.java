package com.presidio.imdbtask.repository;

import com.presidio.imdbtask.entity.Movie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
}
