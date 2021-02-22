package com.presidio.imdbtask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CrudServiceImpl<T, ID, K extends CrudRepository<T, ID>> implements CrudService<T, ID> {

    private final K k;

    @Autowired
    public CrudServiceImpl(K k) {
        this.k = k;
    }

    @Override
    public T add(T entity) {
        return k.save(entity);
    }

    @Override
    public T update(T newData) {
        return k.save(newData);
    }

    @Override
    public T findById(ID id) throws EntityNotFoundException {
        return k.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteById(ID id) {
        k.deleteById(id);
    }

    @Override
    public List<T> findAll() {
        Iterable<T> userIterable = k.findAll();
        return StreamSupport.stream(userIterable.spliterator(), false) // set to true in case we deal with a lot of data
                .collect(Collectors.toList());
    }
}
