package com.presidio.imdbtask.repository;

import com.presidio.imdbtask.entity.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
