package com.qashqade.documentservice.service;

import com.qashqade.documentservice.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Level;

@Service
public class WebMessageService {
    @Autowired
    private WebClient webClient;
    @Autowired
    private MongoMessageService mongoMessageService;

    public Flux<Message> allMessages(int limit){
        long currentTime = System.currentTimeMillis();
        String queryParam = "";
        if (limit>0) queryParam = "?limit="+limit;
        Flux<Message> messageFlux = webClient.get()
            .uri("/messages" + queryParam)
            .retrieve()
            .bodyToFlux(Message.class)
            .log("WebMessageService.allMessage", Level.INFO)
            .doOnComplete(() -> System.out.printf("%s messages have been got from webflux module in %s seconds\n",limit,
                (System.currentTimeMillis() - currentTime)/1000.0))
            .doOnError(e -> System.out.printf("%s can't be obtained from webflux module!\nBecause:\n%s",limit,e.getMessage()));
        return mongoMessageService.saveAll(messageFlux);

    }

    public Mono<Message> getMessageById(Long id) {
        long currentTime = System.currentTimeMillis();
        Mono<Message> messageMono = webClient.get()
            .uri("/messages/" + id)
            .retrieve()
            .bodyToMono(Message.class);
        return mongoMessageService.save(messageMono)
            .log("WebMessageService.getMessageById", Level.INFO)
            .doOnSuccess(m -> System.out.println("documentservice id " + id + "Yes!" + (System.currentTimeMillis() - currentTime)/1000.0))
            .doOnError(e -> System.out.println("documentservice id " + id + "No! "+ e.getMessage()));
    }

}
