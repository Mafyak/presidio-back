package com.presidio.imdbtask.repository;

import com.presidio.imdbtask.entity.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
