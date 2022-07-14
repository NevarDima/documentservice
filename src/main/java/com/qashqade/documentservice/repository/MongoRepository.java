package com.qashqade.documentservice.repository;

import com.qashqade.documentservice.model.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoRepository extends ReactiveMongoRepository<Message,Long> {

}
