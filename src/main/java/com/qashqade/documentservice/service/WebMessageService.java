package com.qashqade.documentservice.service;

import com.qashqade.documentservice.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebMessageService {
    @Autowired
    private WebClient webClient;
    @Autowired
    private MongoMessageService mongoMessageService;

    public Flux<Message> allMessages(int limit){
        long currentTimeToGet = System.currentTimeMillis();
        String queryParam = "";
        if (limit>0) queryParam = "?limit="+limit;
        Flux<Message> messageFlux = webClient.get()
            .uri("/messages" + queryParam)
            .retrieve()
            .bodyToFlux(Message.class)
            .doOnComplete(() -> System.out.println("documentservice " + limit + " Yes! " + (System.currentTimeMillis() - currentTimeToGet)/1000.0))
            .doOnError(e -> System.out.println("documentservice " + limit + "No ;(. " + e.getMessage()));
        return mongoMessageService.saveAll(messageFlux);

    }

    public Mono<Message> getMessageById(Long id) {
        Mono<Message> messageMono = webClient.get()
            .uri("/messages/" + id)
            .retrieve()
            .bodyToMono(Message.class);
        return mongoMessageService.save(messageMono)
            .doOnSuccess(m -> System.out.println("Yes!"))
            .doOnError(e -> System.out.println("No!"+ e.getMessage()));
    }

}
