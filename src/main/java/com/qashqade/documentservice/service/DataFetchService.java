package com.qashqade.documentservice.service;

import com.qashqade.documentservice.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.logging.Level;

@Service
@Slf4j
public class DataFetchService {

    @Autowired
    private WebClient webClient;
    @Autowired
    private TransactionService transactionService;

    public Flux<Transaction> allTransactions(int limit) {
        long currentTime = System.currentTimeMillis();
        String queryParam = "";
        if (limit > 0) queryParam = "?limit=" + limit;
        Flux<Transaction> transactionFlux = webClient.get()
            .uri("/transactions" + queryParam)
            .retrieve()
            .bodyToFlux(Transaction.class)
            .log("TransactionService.allTransactions", Level.INFO, SignalType.ON_ERROR)
            .doOnComplete(() -> log.debug("{} transactions have been got in {} seconds!", limit,
                (System.currentTimeMillis() - currentTime) / 1000.0))
            .doOnError(e -> log.error("{} can't be obtained!", limit, e));
        return transactionService.saveAll(transactionFlux);

    }

    public Mono<Transaction> getTransactionById(Long id) {
        long currentTime = System.currentTimeMillis();
        Mono<Transaction> transactionMono = webClient.get()
            .uri("/transactions/" + id)
            .retrieve()
            .bodyToMono(Transaction.class);
        return transactionService.save(transactionMono)
            .log("TransactionService.getTransactionById", Level.INFO, SignalType.ON_ERROR)
            .doOnSuccess(m -> log.debug("Transaction id {} has been got in {} seconds!", id,
                ((System.currentTimeMillis() - currentTime) / 1000.0)))
            .doOnError(e -> log.error("Transaction id {} can't be obtained!", id, e));
    }

}
