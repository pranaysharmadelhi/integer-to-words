package com.sonatype.assignment.integertowords.services.language.db.tens;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TensRepository extends CrudRepository<Tens, Integer> {

    @Cacheable(value = "tens", key = "#id")
    List<Tens> findAll();
}