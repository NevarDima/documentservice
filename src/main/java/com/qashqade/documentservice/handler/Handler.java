package com.qashqade.documentservice.handler;

import com.qashqade.documentservice.model.Message;
import com.qashqade.documentservice.service.WebMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class Handler {
    private final WebMessageService webMessageService;

    public Mono<ServerResponse> list(ServerRequest request) {
        var limit = Integer.parseInt(request.queryParam("limit").orElse("0"));

        webMessageService.allMessages(limit).doOnComplete(() -> System.out.println("200 messages were successfully saved to mongo db."));

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_NDJSON)
            .body(BodyInserters.fromValue("WebMessageService has got " + limit + " transactions"));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_NDJSON)
            .body(webMessageService.getMessageById(Long.valueOf(request.pathVariable("id"))), Message.class);
    }

}
