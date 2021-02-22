package com.presidio.imdbtask.config;

import com.presidio.imdbtask.dto.GenreDto;
import com.presidio.imdbtask.dto.MovieDto;
import com.presidio.imdbtask.dto.MovieDtoMini;
import com.presidio.imdbtask.dto.ReviewDto;
import com.presidio.imdbtask.entity.Genre;
import com.presidio.imdbtask.entity.Movie;
import com.presidio.imdbtask.entity.Review;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AppConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(CustomModelMapperConverters converters) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.addConverter(converters::convertFromReviewToReviewDto, Review.class, ReviewDto.class);
        modelMapper.addConverter(converters::convertFromMovieToMovieDtoMini, Movie.class, MovieDtoMini.class);
        modelMapper.addConverter(converters::convertFromMovieToMovieDto, Movie.class, MovieDto.class);
        modelMapper.addConverter(converters::convertFromGenreToGenreDto, Genre.class, GenreDto.class);
        return modelMapper;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> customCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //todo: add valid origin here
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
