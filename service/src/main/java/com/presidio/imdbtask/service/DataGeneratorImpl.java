package com.presidio.imdbtask.service;

import com.github.javafaker.Faker;
import com.presidio.imdbtask.entity.Actor;
import com.presidio.imdbtask.entity.Genre;
import com.presidio.imdbtask.entity.Language;
import com.presidio.imdbtask.entity.Movie;
import com.presidio.imdbtask.entity.Review;
import com.presidio.imdbtask.entity.User;
import com.presidio.imdbtask.entity.UserType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@AllArgsConstructor
public class DataGeneratorImpl implements DataGenerator {

    private final ActorService actorService;
    private final UserService userService;
    private final GenreService genreService;
    private final MovieService movieService;
    private final ReviewService reviewService;

    @Override
    public void generate() {
        System.out.println("Generating data");
        Faker faker = new Faker();
        generateGenres();
        generateActors(faker);
        generateUsers(faker);
        generateMovies(faker);
    }

    private void generateGenres() {
        List<String> genresExamples = Arrays.asList("Action", "Drama", "Comedy", "Horror", "SciFi");
        for (int z = 0; z < 5; z++) {
            Genre genre = new Genre();
            genre.setName(genresExamples.get(z));
            genreService.add(genre);
        }
    }

    public void generateActors(Faker faker) {
        for (int i = 0; i < 80; i++) {
            Actor actor = new Actor();
            actor.setFullName(faker.name().fullName());
            actor.setImage(faker.avatar().image());
            actorService.add(actor);
        }
    }

    public void generateMovies(Faker faker) {
        List<Actor> actors = actorService.findAll();
        int totalActors = actors.size();
        List<Genre> allGenres = genreService.findAll();
        int genreSize = allGenres.size();
        List<User> users = userService.findAll();
        int usersSize = users.size();
        for (int i = 0; i < 50; i++) {
            Movie movie = new Movie();
            movie.setTitle(faker.book().title());
            movie.setLang(Language.values()[new Random().nextInt(Language.values().length)]);
            Set<Genre> genres = new HashSet<>();
            for (int z = 1; z < 5; z++) {
                genres.add(allGenres.get(new Random().nextInt(genreSize)));
            }
            movie.setGenre(genres);
            movie.setPlot(faker.gameOfThrones().quote().concat("... ").concat(faker.lorem().fixedString(400)));
            movie.setImage("https://m.media-amazon.com/images/M/MV5BMmEzNTkxYjQtZTc0MC00YTVjLTg5ZTEtZWMwOWVlYzY0NWIwXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_FMjpg_UX1000_.jpg");
            movie.setReleaseDate(LocalDate.of(new Random().nextInt(120) + 1900, new Random().nextInt(12) + 1, new Random().nextInt(28) + 1));
            Set<Actor> cast = new HashSet<>();
            int castSize = 5 + new Random().nextInt(15);
            for (int x = 0; x < castSize; x++) {
                cast.add(actors.get(new Random().nextInt(totalActors)));
            }
            movie.setCast(cast);

            Set<Review> reviews = new HashSet<>();
            int reviewsCount = new Random().nextInt(5) + 5;
            generateReviews(faker, movie, reviews, users, usersSize, reviewsCount);
            movie.setReviews(reviews);

            reviews.stream()
                    .map(Review::getRating)
                    .reduce(Integer::sum)
                    .ifPresent(totalRating -> movie.setRating(totalRating / (double) reviewsCount));

            try {
                movieService.add(movie);
            } catch (Exception e) {
                // Some movies would have the same name
                System.out.println("Can't generate movie");
            }
        }
    }

    private void generateReviews(Faker faker, Movie movie, Set<Review> reviews, List<User> users, int usersSize, int reviewsCount) {
        for (int y = 0; y < reviewsCount; y++) {
            Review review = new Review();
            // Let it be possible that review was written before movie came out, why not, lol? =)
            review.setCreationDate(LocalDate.of(new Random().nextInt(120) + 1900, new Random().nextInt(12) + 1, new Random().nextInt(28) + 1));
            review.setMovie(movie);
            review.setRating(new Random().nextInt(5));
            review.setSummary(faker.harryPotter().quote().concat("... ").concat(faker.lorem().fixedString(10 + new Random().nextInt(100))));
            review.setUser(users.get(new Random().nextInt(usersSize)));
            int likesCount = new Random().nextInt(usersSize);
            Set<User> likes = new HashSet<>();
            for (int x = 0; x < likesCount; x++) {
                likes.add(users.get(new Random().nextInt(usersSize)));
            }
            review.setLikes(likes);
            reviews.add(review);
        }
    }

    private void generateUsers(Faker faker) {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setFullName(faker.name().fullName());
            user.setAvatar("https://i.pinimg.com/originals/a7/43/2f/a7432fc28ec10f5dfd0c0b291856203c.jpg");
            user.setEmail(faker.internet().emailAddress());
            user.setType(UserType.USER);
            user.setRegistrationDate(LocalDate.of(new Random().nextInt(120) + 1900, new Random().nextInt(12) + 1, new Random().nextInt(28) + 1));
            user.setPass("111");
            userService.add(user);
        }
    }
}
