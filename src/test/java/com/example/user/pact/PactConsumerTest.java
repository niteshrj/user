package com.example.user.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.user.controller.Book;
import com.example.user.controller.UserController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "AllBook")
public class PactConsumerTest {

    @Pact(consumer = "User")
    public RequestResponsePact pactAllBooksConfig(PactDslWithProvider builder) {
        return builder.given("All books list exist")
                .uponReceiving("Get all books")
                .path("/book")
                .willRespondWith()
                .status(200)
                .body(PactDslJsonArray.arrayMinLike(3)
                        .stringType("id", "12")
                        .stringType("name", "someBookName")
                        .stringType("price", "500").closeObject()).toPact();
    }

    @Test
    @PactTestFor(pactMethod = "pactForAllBooks", port = "9000")
    public void getAllBooksForAUser(MockServer mockServer) throws JsonProcessingException {

        String expectedValue = "[{\"id\":\"123\",\"name\":\"dummyBookName\",\"price\":500},{\"id\":\"123\",\"name\":\"dummyBookName\",\"price\":500},{\"id\":\"123\",\"name\":\"dummyBookName\",\"price\":500}]";
        UserController userController = new UserController();

        userController.setBaseUrl(mockServer.getUrl());

        List<Book> books = userController.getBooks("123").getBody();

        ObjectMapper obj = new ObjectMapper();
        String actualValue = obj.writeValueAsString(books);

        assertEquals(expectedValue, actualValue);
    }
}
