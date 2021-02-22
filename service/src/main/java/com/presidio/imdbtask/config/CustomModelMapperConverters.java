package com.presidio.imdbtask.config;

import com.presidio.imdbtask.dto.ActorShortDto;
import com.presidio.imdbtask.dto.GenreDto;
import com.presidio.imdbtask.dto.MovieDto;
import com.presidio.imdbtask.dto.MovieDtoMini;
import com.presidio.imdbtask.dto.ReviewDto;
import com.presidio.imdbtask.dto.UserDto;
import com.presidio.imdbtask.entity.Actor;
import com.presidio.imdbtask.entity.Genre;
import com.presidio.imdbtask.entity.Movie;
import com.presidio.imdbtask.entity.Review;
import com.presidio.imdbtask.entity.User;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom converters for model mapper
 */

@Component
public class CustomModelMapperConverters {

    public ReviewDto convertFromReviewToReviewDto(MappingContext<Review, ReviewDto> context) {
        Review review = context.getSource();
        return convertFromReviewToReviewDto(review);
    }

    public MovieDtoMini convertFromMovieToMovieDtoMini(MappingContext<Movie, MovieDtoMini> context) {
        Movie movie = context.getSource();
        return convertFromMovieToMovieDtoMini(movie);
    }

    public MovieDto convertFromMovieToMovieDto(MappingContext<Movie, MovieDto> context) {
        Movie movie = context.getSource();
        return convertFromMovieToMovieDto(movie);
    }

    private MovieDto convertFromMovieToMovieDto(Movie movie) {
        MovieDto movieDto = convertFromMovieToMovieDtoMini(movie);
        movieDto.setReviews(convertFromReviewToReviewDto(movie.getReviews()));
        movieDto.setCast(convertFromActorToActorShortDto(movie.getCast()));
        return movieDto;
    }

    public GenreDto convertFromGenreToGenreDto(MappingContext<Genre, GenreDto> context) {
        Genre source = context.getSource();
        return convertFromGenreToGenreDto(source);
    }

    private GenreDto convertFromGenreToGenreDto(Genre source) {
        GenreDto dto = new GenreDto();
        dto.setId(source.getId());
        dto.setName(source.getName());
        return dto;
    }

    private Set<Long> convertFromUserToUserLikesDto(Set<User> likes) {
        return likes.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }

    private Set<GenreDto> convertFromGenreToGenreDto(Collection<Genre> genreSet) {
        return genreSet.stream()
                .map(this::convertFromGenreToGenreDto)
                .collect(Collectors.toSet());
    }

    public MovieDto convertFromMovieToMovieDtoMini(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setGenre(convertFromGenreToGenreDto(movie.getGenre()));
        dto.setImage(movie.getImage());
        dto.setPlot(movie.getPlot());
        dto.setLang(movie.getLang());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setTitle(movie.getTitle());
        dto.setRating(movie.getRating());
        return dto;
    }

    private Set<ActorShortDto> convertFromActorToActorShortDto(Collection<Actor> genreSet) {
        return genreSet.stream()
                .map(this::convertFromActorToActorShortDto)
                .collect(Collectors.toSet());
    }

    private ActorShortDto convertFromActorToActorShortDto(Actor actor) {
        ActorShortDto dto = new ActorShortDto();
        dto.setId(actor.getId());
        dto.setFullName(actor.getFullName());
        dto.setImage(actor.getImage());
        return dto;
    }

    private Set<ReviewDto> convertFromReviewToReviewDto(Collection<Review> genreSet) {
        return genreSet.stream()
                .map(this::convertFromReviewToReviewDto)
                .collect(Collectors.toSet());
    }

    public ReviewDto convertFromReviewToReviewDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setCreationDate(review.getCreationDate());
        dto.setRating(review.getRating());
        dto.setSummary(review.getSummary());

        dto.setUser(convertFromUserToUserDto(review.getUser()));
        dto.setLikes(convertFromUserToUserLikesDto(review.getLikes()));
        return dto;
    }

    private static UserDto convertFromUserToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setAvatar(user.getAvatar());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setType(user.getType());
        return dto;
    }
}
