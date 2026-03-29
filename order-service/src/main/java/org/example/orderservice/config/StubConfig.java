package org.example.orderservice.config;

import demo.grpc.generated.PriceServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class StubConfig {

    @Bean
    @Primary
    PriceServiceGrpc.PriceServiceBlockingStub priceStub(GrpcChannelFactory channels) {
        return PriceServiceGrpc.newBlockingStub(channels.createChannel("pricing"));
    }
}

