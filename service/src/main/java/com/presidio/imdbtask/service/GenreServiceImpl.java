package com.presidio.imdbtask.service;

import com.presidio.imdbtask.entity.Genre;
import com.presidio.imdbtask.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl extends CrudServiceImpl<Genre, Long, GenreRepository> implements GenreService {

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        super(genreRepository);
    }
}
