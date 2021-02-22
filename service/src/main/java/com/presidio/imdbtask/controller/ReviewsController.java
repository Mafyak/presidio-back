package com.presidio.imdbtask.controller;

import com.presidio.imdbtask.dto.ReviewDto;
import com.presidio.imdbtask.entity.Review;
import com.presidio.imdbtask.entity.User;
import com.presidio.imdbtask.entity.UserType;
import com.presidio.imdbtask.service.ReviewService;
import com.presidio.imdbtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
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
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewsController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    @RolesAllowed({UserType.USER_ROLE, UserType.ADMIN_ROLE})
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto add(@RequestBody Review review, Principal principal) throws Exception {
        String name = principal.getName();
        User currentUser = userService.findUserByLogin(name);
        review.setUser(currentUser);
        review.setCreationDate(LocalDate.now());
        review.setLikes(new HashSet<>());
        return modelMapper.map(reviewService.add(review), ReviewDto.class);
    }

    @RolesAllowed({UserType.USER_ROLE, UserType.ADMIN_ROLE})
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ReviewDto update(Principal principal, @RequestBody ReviewDto newData) {
        String userName = principal.getName();
        User currentUser = userService.findUserByLogin(userName);
        if (currentUser.getId().equals(newData.getUser().getId())) {
            Review currentReview = reviewService.findById(newData.getId());
            modelMapper.map(newData, currentReview);
            return modelMapper.map(reviewService.update(currentReview), ReviewDto.class);
        } else throw new UnauthorizedUserException("You're not allowed to update reviews of other users");
    }

    @RolesAllowed({UserType.USER_ROLE, UserType.ADMIN_ROLE})
    @PutMapping(value = "/removeLike/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewDto removeLike(Principal principal, @PathVariable("id") Long id) {
        String userName = principal.getName();
        User currentUser = userService.findUserByLogin(userName);
        Review currentReview = reviewService.findById(id);
        currentReview.getLikes().remove(currentUser);
        return modelMapper.map(reviewService.update(currentReview), ReviewDto.class);
    }

    @RolesAllowed({UserType.USER_ROLE, UserType.ADMIN_ROLE})
    @PutMapping(value = "/addLike/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewDto addLike(Principal principal, @PathVariable("id") Long id) {
        String userName = principal.getName();
        User currentUser = userService.findUserByLogin(userName);
        Review currentReview = reviewService.findById(id);
        currentReview.getLikes().add(currentUser);
        return modelMapper.map(reviewService.update(currentReview), ReviewDto.class);
    }

    @GetMapping(value = "/{id}")
    public ReviewDto findById(@PathVariable("id") Long id) {
        return modelMapper.map(reviewService.findById(id), ReviewDto.class); // returns null in case id is wrong. @todo improve.
    }

    @GetMapping
    public List<ReviewDto> findAll() {
        return reviewService.findAll().stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
    }

    @RolesAllowed({UserType.USER_ROLE, UserType.ADMIN_ROLE})
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("id") Long id) {
        reviewService.deleteById(id);
    }
}
