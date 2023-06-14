package com.hfc.webflux.repository;

import com.hfc.webflux.entity.Person;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by hfc on 2023/6/2.
 */
public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {

    @Query("select * from test")
    Flux<Person> getAllPerson();

    @Query("select * from test where name = :name")
    Mono<Person> getPersonByName(String name);

}
