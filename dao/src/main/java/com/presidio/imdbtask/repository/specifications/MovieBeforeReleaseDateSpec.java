package com.presidio.imdbtask.repository.specifications;

import com.presidio.imdbtask.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

@Setter
@AllArgsConstructor
public class MovieBeforeReleaseDateSpec implements Specification<Movie> {

    private final LocalDate releaseDate;

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lessThan(root.get("releaseDate"), releaseDate);
    }
}
