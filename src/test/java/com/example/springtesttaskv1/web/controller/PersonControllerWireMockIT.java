package com.example.springtesttaskv1.web.controller;

import com.example.springtesttaskv1.util.PersonTestData;
import com.example.springtesttaskv1.web.response.PersonResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WireMockTest(httpPort = 8181)
class PersonControllerWireMockIT {

    private final String BASE_URL = "http://localhost:8181";
    private final String PERSONS_URL = "/api/v1/persons/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetPersonByUUID() {
        //given
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer =  new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        PersonResponse personResponse = PersonTestData.builder()
                .build()
                .buildPersonResponse();
        JsonNode jsonPerson = objectMapper.valueToTree(personResponse);

        //when
        stubFor(get(urlPathMatching(PERSONS_URL + personResponse.getUuidPerson()))
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withJsonBody(jsonPerson)));

        PersonResponse expected = restTemplate.getForObject(BASE_URL + PERSONS_URL + personResponse.getUuidPerson(), PersonResponse.class);

        //then
        assertEquals(expected, personResponse);
    }
}