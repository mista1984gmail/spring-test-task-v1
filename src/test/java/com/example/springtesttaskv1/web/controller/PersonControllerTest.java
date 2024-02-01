package com.example.springtesttaskv1.web.controller;

import com.example.springtesttaskv1.entity.dto.PersonDto;
import com.example.springtesttaskv1.mapper.PersonMapper;
import com.example.springtesttaskv1.service.PersonService;
import com.example.springtesttaskv1.util.ConstantsForTest;
import com.example.springtesttaskv1.util.PersonTestData;
import com.example.springtesttaskv1.web.request.PersonRequest;
import com.example.springtesttaskv1.web.response.PersonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    private final String RESOURCE_URL = "/api/v1/persons";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonMapper personMapper;


    @Test
    void shouldGetPersonByUUID() throws Exception {
        //given
        UUID personUUID = ConstantsForTest.UUID_PERSON;
        PersonDto person = PersonTestData.builder()
                .build()
                .buildPersonDto();

        PersonResponse personResponse = PersonTestData.builder()
                .build()
                .buildPersonResponse();

        //when

        when(personService.findByUUID(personUUID)).thenReturn(person);
        when(personMapper.dtoToResponse(person)).thenReturn(personResponse);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(RESOURCE_URL + "/" + personUUID)
                        .content(objectMapper.findAndRegisterModules().writeValueAsString(personResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ivan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Ivanov"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sex").value("MALE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportSeries").value("KH"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumber").value("1334567"));

        verify(personService, times(1)).findByUUID(personUUID);
    }

    @Test
    void shouldSavePerson() throws Exception {
        //given
        PersonRequest personRequest = PersonTestData.builder()
                .build()
                .buildPersonRequest();
        PersonDto personDto = PersonTestData.builder()
                .build()
                .buildPersonDto();
        PersonResponse personResponse = PersonTestData.builder()
                .build()
                .buildPersonResponse();
        UUID houseUUID = ConstantsForTest.UUID_HOUSE;

        //when
        when(personService.save(personRequest, houseUUID)).thenReturn(personDto);
        when(personMapper.dtoToResponse(personDto)).thenReturn(personResponse);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE_URL)
                        .content(objectMapper.findAndRegisterModules().writeValueAsString(personResponse))
                        .param("house_uuid", "c9362a9e-b783-46f9-9ffb-c8df6461634b")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ivan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Ivanov"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sex").value("MALE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportSeries").value("KH"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumber").value("1334567"));

        verify(personService, times(1)).save(personRequest, houseUUID);
    }

    @Test
    void shouldFailSave() throws Exception {
        //given
        PersonRequest personRequest = PersonTestData.builder()
                .build()
                .buildPersonRequest();
        PersonDto personDto = PersonTestData.builder()
                .build()
                .buildPersonDto();
        PersonResponse personResponse = PersonTestData.builder()
                .build()
                .buildPersonResponse();
        UUID houseUUID = ConstantsForTest.UUID_HOUSE;

        //when
        when(personService.save(personRequest, houseUUID)).thenReturn(personDto);
        when(personMapper.dtoToResponse(personDto)).thenReturn(personResponse);

        //then
        mockMvc.perform(
                        MockMvcRequestBuilders.post(RESOURCE_URL)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(personResponse)))
                .andExpect(status().is5xxServerError())
                .andDo(print());

        verify(personService, never()).save(personRequest, houseUUID);

    }
}