package com.qashqade.documentservice.service;

import com.qashqade.documentservice.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

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
        return webClient.get()
            .uri("/messages" + queryParam)
            .retrieve()
            .bodyToFlux(Message.class)
            .timeout(Duration.ofMillis(10000))
            .doOnComplete(() -> System.out.println("documentservice " + limit + " Yes! " + (System.currentTimeMillis() - currentTime)/1000.0))
            .doOnError((e) -> System.out.println("documentservice " + limit + "No ;(. " + e.getMessage()));

    }

    public Mono<Message> getMessageById(Long id)
    {
        Mono<Message> messageMono = webClient.get()
            .uri("/messages/" + id)
            .retrieve()
            /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                    clientResponse -> Mono.empty())*/
            .bodyToMono(Message.class);
        return mongoMessageService.save(messageMono)
            .doOnSuccess(m -> System.out.println("Yes!"))
            .doOnError(e -> System.out.println("No!"+ e.getMessage()));
    }

}
