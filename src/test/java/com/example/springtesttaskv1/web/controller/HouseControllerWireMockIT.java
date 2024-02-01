package com.example.springtesttaskv1.web.controller;

import com.example.springtesttaskv1.util.HouseTestData;
import com.example.springtesttaskv1.web.response.HouseResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WireMockTest(httpPort = 8181)
class HouseControllerWireMockIT {

    private final String BASE_URL = "http://localhost:8181";
    private final String HOUSES_URL = "/api/v1/houses/";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void shouldGetHouseByUUID() {
        //given
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer =  new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        HouseResponse houseResponse = HouseTestData.builder()
                .build()
                .buildHouseResponse();
        JsonNode jsonHouse = objectMapper.valueToTree(houseResponse);

        //when
        stubFor(get(urlPathMatching(HOUSES_URL + houseResponse.getUuidHouse()))
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withJsonBody(jsonHouse)));

        HouseResponse expected = restTemplate.getForObject(BASE_URL + HOUSES_URL + houseResponse.getUuidHouse(), HouseResponse.class);

        //then
        assertEquals(expected, houseResponse);
    }
}