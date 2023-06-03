package com.hfc.webflux.handler;

import com.hfc.webflux.entity.Person;
import com.hfc.webflux.repository.PersonRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

/**
 * Created by hfc on 2023/6/2.
 */
@Component
public class PersonReactiveHandler {

    private final PersonRepository personRepository;

    public PersonReactiveHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @NonNull
    public Mono<ServerResponse> getPersonById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(this.personRepository.findById(id), Person.class));
    }

    @NonNull
    public Mono<ServerResponse> existedPersonByid(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(this.personRepository.existsById(id), Boolean.class));
    }

}
