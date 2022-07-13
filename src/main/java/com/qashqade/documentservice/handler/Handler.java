package com.qashqade.documentservice.handler;

import com.qashqade.documentservice.model.Message;
import com.qashqade.documentservice.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
public class Handler {
    private final MessageService messageService;

    public Mono<ServerResponse> list(ServerRequest request) {
        var limit = Integer.parseInt(request.queryParam("limit").orElse("0"));

        Flux<Message> publisher = messageService.allMessages(limit);
        publisher.map(m -> m.getId()).subscribe(id -> System.out.print(id+","));
        System.out.println("\n--------------\n--------------\n");

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(limit +" trxs"));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(messageService.getMessageById(Long.valueOf(request.pathVariable("id"))), Message.class);
    }

}
