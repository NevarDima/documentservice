package com.qashqade.documentservice.config;

import com.qashqade.documentservice.handler.Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration(proxyBeanMethods = false)
public class Router {

    @Bean
    public RouterFunction<ServerResponse> route(Handler transactionHandler) {
        return RouterFunctions
            .route(GET("/transactions"), transactionHandler::list)
            .andRoute(GET("/transactions/{id}"), transactionHandler::getById);
    }

}
