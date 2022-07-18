package com.qashqade.documentservice.service;

import com.qashqade.documentservice.model.Message;
import com.qashqade.documentservice.repository.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.logging.Level;

@Service
public class MongoMessageService {

    @Autowired
    private MongoRepository mongoRepository;

    public Flux<Message> allMessages() {
        return mongoRepository.findAll();
    }

    public Mono<Message> getById(Long id) {
        return mongoRepository.findById(id);
    }

    public Mono<Message> save(Mono<Message> messageMono) {
        long currentTime = System.currentTimeMillis();
        final Long[] id = new Long[1];
        return messageMono.flatMap(message -> {
                id[0] = message.getId();
                return mongoRepository.insert(message);
            })
            .log("MongoMessageService.save", Level.INFO, SignalType.ON_ERROR)
            .doOnSuccess(m -> System.out.println("id " + id[0] + " message saved! " + (System.currentTimeMillis() - currentTime) / 1000.0))
            .doOnError(e -> System.out.println("Message id " + id[0] + " can't be saved!" + e.getMessage()));
    }

    public Flux<Message> saveAll(Flux<Message> messageFlux) {
        long currentTime = System.currentTimeMillis();
        messageFlux.flatMap(mongoRepository::insert)
            .log("MongoMessageService.saveAll", Level.INFO, SignalType.ON_ERROR)
            .doOnComplete(() -> System.out.println("All message saved! " + (System.currentTimeMillis() - currentTime) / 1000.0))
            .doOnError(e -> System.out.println("All messages can't be saved!" + e.getMessage()))
            .subscribe();
        return messageFlux;
    }
}
