package com.presidio.imdbtask.repository.specifications.builder;

import com.presidio.imdbtask.entity.Movie;
import com.presidio.imdbtask.repository.specifications.MovieAfterReleaseDateSpec;
import com.presidio.imdbtask.repository.specifications.MovieBeforeReleaseDateSpec;
import com.presidio.imdbtask.repository.specifications.MovieByActorId;
import com.presidio.imdbtask.repository.specifications.MovieByGenre;
import com.presidio.imdbtask.repository.specifications.MovieByTitle;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CompanySpecBuilderImpl implements CompanySpecBuilder {

    @Override
    public Specification<Movie> build(String title, LocalDate releasedBeforeDate, LocalDate releasedAfterDate, String genre, Long actorId) {
        List<Specification<Movie>> specs = new ArrayList<>();
        if (title != null) {
            specs.add(new MovieByTitle(title));
        }
        if (releasedBeforeDate != null) {
            specs.add(new MovieBeforeReleaseDateSpec(releasedBeforeDate));
        }
        if (releasedAfterDate != null) {
            specs.add(new MovieAfterReleaseDateSpec(releasedAfterDate));
        }
        if (genre != null) {
            specs.add(new MovieByGenre(genre));
        }
        if (actorId != null) {
            specs.add(new MovieByActorId(actorId));
        }

        // default spec, which specifies all records in the DB. used when all params are null
        Specification<Movie> spec = (Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.and();
        // call spec.and(specInList) one by one for all specs in this list
        return specs.stream().reduce(spec, Specification::and);
    }
}
