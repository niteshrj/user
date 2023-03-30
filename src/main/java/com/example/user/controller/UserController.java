package com.example.user.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private String baseUrl = "localhost";

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @GetMapping(value = "/{id}/books")
    public ResponseEntity<List<Book>> getBooks(@PathVariable String id) {
        RestTemplate restTemplate = new RestTemplate();
        if(id.equals("123")) {
            return restTemplate.exchange(
                    "http://localhost:9000/books",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Book>>() {}
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
