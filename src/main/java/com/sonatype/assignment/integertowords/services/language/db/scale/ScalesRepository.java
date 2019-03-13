package com.sonatype.assignment.integertowords.services.language.db.scale;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScalesRepository extends CrudRepository<Scales, Integer> {

    @Cacheable(value = "scales", key = "#id")
    List<Scales> findAllByNumberingSystemIdAndNoOfZerosStartBeforeOrderByNoOfZerosStartDesc(int numberingSystemId, int noOfZerosStart);

}