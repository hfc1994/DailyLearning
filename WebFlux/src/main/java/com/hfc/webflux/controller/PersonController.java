package com.hfc.webflux.controller;

import com.hfc.webflux.entity.Person;
import com.hfc.webflux.repository.PersonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by hfc on 2023/6/3.
 */
@RestController
@RequestMapping("/v1/mvc")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/person/{id}")
    public Mono<Person> getPersonById(@PathVariable(name = "id") long id) {
        return this.personRepository.findById(id);
    }

    @GetMapping("/person/{id}/exist")
    public Mono<Boolean> existPersonById(@PathVariable(name = "id") long id) {
        return this.personRepository.existsById(id);
    }

    @GetMapping("/person/all")
    public Flux<Person> getAllPerson() {
        return this.personRepository.getAllPerson();
    }

    @GetMapping("/person/name/{name}")
    public Mono<Person> getPersonByName(@PathVariable(name = "name") String name) {
        return this.personRepository.getPersonByName(name);
    }

}
