package com.qashqade.documentservice.repository;

import com.qashqade.documentservice.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MongoRepository extends ReactiveMongoRepository<Transaction, Long> {

    Flux<Transaction> findAllLimitBy(Pageable pageable);
}
