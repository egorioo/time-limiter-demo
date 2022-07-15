package com.example.demospring.service;

import com.example.demospring.MyException;
import com.example.demospring.dto.ProductRatingDto;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

@Service
public class RatingServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${rating.service.endpoint}")
    private String ratingService;

    @TimeLimiter(name = "ratingService")
    public CompletableFuture<ProductRatingDto> getProductRatingDto(int productId){
//        if (productId != 10)
//            throw new MyException("");

        Supplier<ProductRatingDto> supplier = () ->
                this.restTemplate.getForEntity(this.ratingService + productId, ProductRatingDto.class)
                        .getBody();
        return CompletableFuture.supplyAsync(supplier);
    }

//    private CompletionStage<ProductRatingDto> getDefault(int productId, Throwable throwable){
//        System.out.println("default");
//        return CompletableFuture.supplyAsync(() -> ProductRatingDto.of(0, Collections.emptyList()));
//    }
}
