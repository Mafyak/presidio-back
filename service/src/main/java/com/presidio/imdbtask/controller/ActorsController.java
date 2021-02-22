package com.presidio.imdbtask.controller;

import com.presidio.imdbtask.dto.ActorDto;
import com.presidio.imdbtask.dto.ActorShortDto;
import com.presidio.imdbtask.entity.Actor;
import com.presidio.imdbtask.entity.UserType;
import com.presidio.imdbtask.service.ActorService;
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
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/actors")
public class ActorsController {

    private final ActorService actorService;
    private final ModelMapper modelMapper;

    @PostMapping
    @RolesAllowed(UserType.ADMIN_ROLE)
    @ResponseStatus(HttpStatus.CREATED)
    public ActorDto add(@RequestBody Actor movie) throws Exception {
        return modelMapper.map(actorService.add(movie), ActorDto.class);
    }

    @RolesAllowed(UserType.ADMIN_ROLE)
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ActorDto update(Principal principal, @RequestBody Actor actor) {
        return modelMapper.map(actorService.update(actor), ActorDto.class);
    }

    @GetMapping(value = "/{id}")
    @RolesAllowed(UserType.ADMIN_ROLE) // @todo for testing purposes only
    public ActorDto findById(@PathVariable("id") Long id) {
        return modelMapper.map(actorService.findById(id), ActorDto.class); // returns null in case id is wrong. @todo improve.
    }

    @GetMapping
    public List<ActorShortDto> findAll() {
        return actorService.findAll().stream()
                .map(actor -> modelMapper.map(actor, ActorShortDto.class))
                .collect(Collectors.toList());
    }

    @RolesAllowed(UserType.ADMIN_ROLE)
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        actorService.deleteById(id);
    }
}
