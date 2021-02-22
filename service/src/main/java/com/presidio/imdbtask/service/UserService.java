package com.presidio.imdbtask.service;

import com.presidio.imdbtask.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends CrudService<User, Long> {

    User findUserByLogin(String login); // todo add security

    Page<User> findAll(Pageable pageable);
}
