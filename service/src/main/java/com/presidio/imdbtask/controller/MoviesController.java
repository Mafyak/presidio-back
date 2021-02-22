package com.presidio.imdbtask.controller;

import com.presidio.imdbtask.dto.MovieDto;
import com.presidio.imdbtask.dto.MovieDtoMini;
import com.presidio.imdbtask.entity.Movie;
import com.presidio.imdbtask.entity.UserType;
import com.presidio.imdbtask.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MoviesController {

    private final MovieService movieService;
    private final ModelMapper modelMapper;

    @PostMapping
    @RolesAllowed(UserType.ADMIN_ROLE)
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDtoMini add(@RequestBody Movie movie) throws Exception {
        return modelMapper.map(movieService.add(movie), MovieDtoMini.class);
    }

    @RolesAllowed(UserType.ADMIN_ROLE)
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public MovieDtoMini update(Principal principal, @RequestBody Movie movie) {
        return modelMapper.map(movieService.update(movie), MovieDtoMini.class);
    }

    @GetMapping(value = "/{id}")
    public MovieDto findById(@PathVariable("id") Long id) {
        return modelMapper.map(movieService.findById(id), MovieDto.class); // returns null in case id is wrong. @todo improve.
    }

    @GetMapping
    public Page<MovieDtoMini> find(String title, String releasedBeforeDate, String releasedAfterDate, String genre, Long actorId, Pageable pageable) {
        LocalDate beforeDate = releasedBeforeDate != null ? LocalDate.parse(releasedBeforeDate) : null;
        LocalDate afterDate = releasedAfterDate != null ? LocalDate.parse(releasedAfterDate) : null;
        return movieService.find(title, beforeDate, afterDate, genre, actorId, pageable)
                .map(movie -> modelMapper.map(movie, MovieDtoMini.class));
    }

    @RolesAllowed(UserType.ADMIN_ROLE)
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        movieService.deleteById(id);
    }
}
