package com.presidio.imdbtask.controller;

import com.presidio.imdbtask.dto.GenreDto;
import com.presidio.imdbtask.entity.Genre;
import com.presidio.imdbtask.entity.UserType;
import com.presidio.imdbtask.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;
    private final ModelMapper modelMapper;

    @PostMapping
    @RolesAllowed(UserType.ADMIN_ROLE)
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto add(@RequestBody String genreName) throws Exception {
        Genre genre = new Genre();
        genre.setName(genreName);
        return modelMapper.map(genreService.add(genre), GenreDto.class);
    }

    @RolesAllowed(UserType.ADMIN_ROLE)
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GenreDto update(@RequestBody Genre genre) {
        Genre perDb = genreService.findById(genre.getId());
        perDb.setName(genre.getName());
        return modelMapper.map(genreService.update(perDb), GenreDto.class);
    }

    @GetMapping(value = "/{id}")
    public GenreDto findById(@PathVariable("id") Long id) {
        return modelMapper.map(genreService.findById(id), GenreDto.class);
    }

    @GetMapping
    public List<GenreDto> findAll() {
        return genreService.findAll().stream()
                .map(genre -> modelMapper.map(genre, GenreDto.class))
                .collect(Collectors.toList());
    }

    @RolesAllowed(UserType.ADMIN_ROLE)
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        genreService.deleteById(id);
    }
}
