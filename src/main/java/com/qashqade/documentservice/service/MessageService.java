package com.qashqade.documentservice.service;

import com.qashqade.documentservice.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@AllArgsConstructor
public class MessageService {
    WebClient webClient;

    public Flux<Message> allMessages(int limit){
        String queryParam = "";
        if (limit>0) queryParam = "?limit="+limit;
        return webClient.get()
            .uri("/messages"+queryParam)
            .retrieve()
            .bodyToFlux(Message.class)
            .timeout(Duration.ofMillis(10000));
    }

    public Mono<Message> getMessageById(Long id)
    {
        return webClient.get()
            .uri("/messages/" + id)
            .retrieve()
            /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                    clientResponse -> Mono.empty())*/
            .bodyToMono(Message.class);
    }

}
