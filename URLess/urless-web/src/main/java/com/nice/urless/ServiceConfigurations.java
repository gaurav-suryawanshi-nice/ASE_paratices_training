package com.nice.urless;

import gateway.URLGateway;
import gateway.URLGatewayFake;
import generator.IDGenerator;
import generator.SHA1IdGenerator;
import interactor.ShortenerInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import usecases.ShortenerUseCase;

@Configuration
public class ServiceConfigurations {
    @Bean
    public ShortenerUseCase shortener(URLGateway urlGateway, IDGenerator idGenerator) {
        return new ShortenerInteractor(urlGateway, idGenerator);
    }

    @Bean
    public URLGateway urlGatewayFake() {
        return new URLGatewayFake();
    }

    @Bean
    public IDGenerator sha1IdGenerator() {
        return new SHA1IdGenerator();
    }
}
