package com.sonatype.assignment.integertowords.services.language.db.small_numbers;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SmallNumbersRepository extends CrudRepository<SmallNumbers, Integer> {

    @Cacheable(value = "smallnumbers", key = "#id")
    List<SmallNumbers> findAll();


}