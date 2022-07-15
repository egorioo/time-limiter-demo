package com.example.demospring.controller;



import com.example.demospring.MyException;
import com.example.demospring.dto.ProductDto;
import com.example.demospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("{productId}")
    public ProductDto getProduct(@PathVariable int productId) throws ExecutionException, InterruptedException {
        return this.productService.getProductDto(productId).toCompletableFuture().get();
    }

    @ExceptionHandler(value = MyException.class)
    public ResponseEntity<Object> handleEx(MyException ex) {
        return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TimeoutException.class)
    public ResponseEntity<Object> handleTimeout(TimeoutException ex) {
        System.out.println("error");
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.GATEWAY_TIMEOUT);
    }
}
