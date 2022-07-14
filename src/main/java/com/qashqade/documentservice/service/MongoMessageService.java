package com.qashqade.documentservice.service;

import com.qashqade.documentservice.model.Message;
import com.qashqade.documentservice.repository.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MongoMessageService {

    @Autowired
    private MongoRepository mongoRepository;

    public Flux<Message> allMessages(){
        return mongoRepository.findAll();
    }

    public Mono<Message> getById(Long id){
        return mongoRepository.findById(id);
    }

    public Mono<Message> save (Mono<Message> messageMono){
        return messageMono.flatMap(mongoRepository::insert);
    }

//    public Flux<Message> saveAll(Flux<Message> messageFlux){
//        return messageFlux.map(mongoRepository::saveAll);
//    }
}
