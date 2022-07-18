package com.qashqade.documentservice.service;

import com.qashqade.documentservice.model.Transaction;
import com.qashqade.documentservice.repository.MongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.logging.Level;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private MongoRepository mongoRepository;

    public Flux<Transaction> allTransactions() {
        return mongoRepository.findAll();
    }

    public Mono<Transaction> getById(Long id) {
        return mongoRepository.findById(id);
    }

    public Mono<Transaction> save(Mono<Transaction> transactionMono) {
        long currentTime = System.currentTimeMillis();
        final Long[] id = new Long[1];
        return transactionMono.flatMap(transaction -> {
                id[0] = transaction.getId();
                return mongoRepository.insert(transaction);
            })
            .log("TransactionsService.save", Level.INFO, SignalType.ON_ERROR)
            .doOnSuccess(m -> log.debug("id {} transaction saved for {} seconds!", id[0], (System.currentTimeMillis() - currentTime) / 1000.0))
            .doOnError(e -> log.error("Transaction id {} can't be saved!", id[0], e));
    }

    public Flux<Transaction> saveAll(Flux<Transaction> transactionFlux) {
        long currentTime = System.currentTimeMillis();
        transactionFlux.flatMap(mongoRepository::insert)
            .log("TransactionsService.saveAll", Level.INFO, SignalType.ON_ERROR)
            .doOnComplete(() -> log.debug("All transactions saved for {} seconds!", (System.currentTimeMillis() - currentTime) / 1000.0))
            .doOnError(e -> log.error("All transactions can't be saved!", e))
            .subscribe();
        return transactionFlux;
    }
}
