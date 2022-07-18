package com.qashqade.documentservice.handler;

import com.qashqade.documentservice.model.Transaction;
import com.qashqade.documentservice.service.DataFetchService;
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

    private final DataFetchService dataFetchService;

    public Mono<ServerResponse> list(ServerRequest request) {
        var limit = Integer.parseInt(request.queryParam("limit").orElse("0"));

        dataFetchService.allTransactions(limit);

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_NDJSON)
            .body(BodyInserters.fromValue("DataFetchService has got " + limit + " transactions"));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_NDJSON)
            .body(dataFetchService.getTransactionById(Long.valueOf(request.pathVariable("id"))), Transaction.class);
    }

}
