package com.presidio.imdbtask.repository;

import com.presidio.imdbtask.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    User findFirstByEmail(String email);

}
