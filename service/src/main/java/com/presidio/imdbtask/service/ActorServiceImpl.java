package com.presidio.imdbtask.service;

import com.presidio.imdbtask.entity.Actor;
import com.presidio.imdbtask.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl extends CrudServiceImpl<Actor, Long, ActorRepository> implements ActorService {

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository) {
        super(actorRepository);
    }
}
