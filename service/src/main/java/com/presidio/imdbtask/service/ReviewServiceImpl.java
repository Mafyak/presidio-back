package com.presidio.imdbtask.service;

import com.presidio.imdbtask.entity.Review;
import com.presidio.imdbtask.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl extends CrudServiceImpl<Review, Long, ReviewRepository> implements ReviewService {

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        super(reviewRepository);
    }
}
