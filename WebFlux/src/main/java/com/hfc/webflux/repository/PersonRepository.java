package com.hfc.webflux.repository;

import com.hfc.webflux.entity.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Created by hfc on 2023/6/2.
 */
public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {
}
