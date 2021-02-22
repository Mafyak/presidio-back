package com.presidio.imdbtask.service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface CrudService<T, ID> {
    T add(T entity);

    T update(T newData);

    T findById(ID id) throws EntityNotFoundException;

    void deleteById(ID id);

    List<T> findAll();
}

