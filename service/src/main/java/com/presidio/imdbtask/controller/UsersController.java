package com.presidio.imdbtask.controller;

import com.presidio.imdbtask.dto.UserDto;
import com.presidio.imdbtask.entity.User;
import com.presidio.imdbtask.entity.UserType;
import com.presidio.imdbtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
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
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto add(@RequestBody UserDto user) throws Exception {
        // not implemented
        throw new Exception("service is not implemented yet.");
    }

    @RolesAllowed({UserType.USER_ROLE, UserType.ADMIN_ROLE})
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(Principal principal, @RequestBody User user) {
        String userName = principal.getName();
        User currentUser = userService.findUserByLogin(userName);
        if (currentUser.getId().equals(user.getId()) || currentUser.getType() == UserType.ADMIN) {
            return modelMapper.map(userService.update(user), UserDto.class);
        } else throw new UnauthorizedUserException("You're not allowed to change others data");
    }

    @GetMapping(value = "/{id}")
    public UserDto findById(@PathVariable("id") Long id) {
        return modelMapper.map(userService.findById(id), UserDto.class); // returns null in case id is wrong. @todo improve.
    }

    @GetMapping(value = "/userInfo")
    public UserDto getUserInfo(Principal principal) {
        String userName = principal.getName();
        return modelMapper.map(userService.findUserByLogin(userName), UserDto.class);
    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @RolesAllowed(UserType.ADMIN_ROLE)
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }
}
