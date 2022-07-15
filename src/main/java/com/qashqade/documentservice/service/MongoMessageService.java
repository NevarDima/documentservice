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
        long currentTime = System.currentTimeMillis();
        return messageMono.flatMap(mongoRepository::insert)
            .doOnSuccess(m -> System.out.println("id " + m.getId() + " message saved! " + (System.currentTimeMillis() - currentTime)/1000.0))
            .doOnError(e -> System.out.println("Message can't be saved ().()"+e.getMessage()));
    }

    public Flux<Message> saveAll(Flux<Message> messageFlux){
        long currentTime = System.currentTimeMillis();
        messageFlux.flatMap(mongoRepository::insert)
            .doOnComplete(() -> System.out.println("All message saved! " + (System.currentTimeMillis() - currentTime)/1000.0))
            .doOnError(e -> System.out.println("All messages can't be saved ().()"+e.getMessage()))
            .subscribe();
        return messageFlux;
    }
}
