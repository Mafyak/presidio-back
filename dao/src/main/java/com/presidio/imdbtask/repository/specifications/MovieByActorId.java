package com.presidio.imdbtask.repository.specifications;

import com.presidio.imdbtask.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Getter
@AllArgsConstructor
public class MovieByActorId implements Specification<Movie> {

    private final Long actorId;

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.join("cast").get("id"), actorId);
    }
}