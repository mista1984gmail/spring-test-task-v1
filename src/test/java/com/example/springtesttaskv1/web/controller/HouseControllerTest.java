package com.example.springtesttaskv1.web.controller;

import com.example.springtesttaskv1.entity.dto.HouseDto;
import com.example.springtesttaskv1.mapper.HouseMapper;
import com.example.springtesttaskv1.service.HouseSearchService;
import com.example.springtesttaskv1.service.HouseService;
import com.example.springtesttaskv1.util.ConstantsForTest;
import com.example.springtesttaskv1.util.HouseTestData;
import com.example.springtesttaskv1.web.request.HouseRequest;
import com.example.springtesttaskv1.web.response.HouseResponse;
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

@WebMvcTest(HouseController.class)
class HouseControllerTest {

    private final String RESOURCE_URL = "/api/v1/houses";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HouseService houseService;

    @MockBean
    private HouseMapper houseMapper;;

    @MockBean
    private HouseSearchService houseSearchService;

    @Test
    void shouldGetHouseByUUID() throws Exception {
        //given
        UUID houseUUID = ConstantsForTest.UUID_HOUSE;
        HouseDto house = HouseTestData.builder()
                .build()
                .buildHouseDto();

        HouseResponse houseResponse = HouseTestData.builder()
                .build()
                .buildHouseResponse();

        //when
        when(houseService.findByUUID(houseUUID)).thenReturn(house);
        when(houseMapper.dtoToResponse(house)).thenReturn(houseResponse);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(RESOURCE_URL + "/" + houseUUID)
                        .content(objectMapper.findAndRegisterModules().writeValueAsString(houseResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuidHouse").value("c9362a9e-b783-46f9-9ffb-c8df6461634b"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.area").value("58.2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country").value("RB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Minsk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Pr. Pobeditelej"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value("156"));

        verify(houseService, times(1)).findByUUID(houseUUID);
    }

    @Test
    void shouldSaveHouse() throws Exception {
        //given
        HouseRequest houseRequest = HouseTestData.builder()
                .build()
                .buildHouseRequest();
        HouseDto houseDto = HouseTestData.builder()
                .build()
                .buildHouseDto();
        HouseResponse houseResponse = HouseTestData.builder()
                .build()
                .buildHouseResponse();

        //when
        when(houseService.save(houseRequest)).thenReturn(houseDto);
        when(houseMapper.dtoToResponse(houseDto)).thenReturn(houseResponse);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE_URL)
                        .content(objectMapper.findAndRegisterModules().writeValueAsString(houseResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuidHouse").value("c9362a9e-b783-46f9-9ffb-c8df6461634b"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.area").value("58.2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country").value("RB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Minsk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Pr. Pobeditelej"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value("156"));

        verify(houseService, times(1)).save(houseRequest);
    }

    @Test
    void shouldFailSave() throws Exception {
        //given
        HouseRequest houseRequest = HouseTestData.builder()
                .build()
                .buildHouseRequest();
        HouseDto houseDto = HouseTestData.builder()
                .build()
                .buildHouseDto();
        HouseResponse houseResponse = HouseTestData.builder()
                .build()
                .buildHouseResponse();

        //when
        when(houseService.save(houseRequest)).thenReturn(houseDto);
        when(houseMapper.dtoToResponse(houseDto)).thenReturn(houseResponse);

        //then
        mockMvc.perform(
                        MockMvcRequestBuilders.post(RESOURCE_URL)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().is5xxServerError())
                .andDo(print());

        verify(houseService, never()).save(houseRequest);

    }
}