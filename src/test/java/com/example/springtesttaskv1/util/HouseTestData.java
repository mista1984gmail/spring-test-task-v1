package com.example.springtesttaskv1.util;

import com.example.springtesttaskv1.entity.dto.HouseDto;
import com.example.springtesttaskv1.entity.model.House;
import com.example.springtesttaskv1.web.request.HouseRequest;
import com.example.springtesttaskv1.web.response.HouseResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class HouseTestData {

    @Builder.Default
    private Long id = ConstantsForTest.ID;

    @Builder.Default
    private UUID uuidHouse = ConstantsForTest.UUID_HOUSE;

    @Builder.Default
    private Double area = ConstantsForTest.AREA;

    @Builder.Default
    private String country = ConstantsForTest.COUNTRY;

    @Builder.Default
    private String city = ConstantsForTest.CITY;

    @Builder.Default
    private String street = ConstantsForTest.STREET;

    @Builder.Default
    private Integer number = ConstantsForTest.NUMBER;

    @Builder.Default
    private LocalDateTime createDate = ConstantsForTest.CREATE_DATE;

    public House buildHouse() {
        return new House(id, uuidHouse, area, country, city, street, number, createDate);
    }

    public HouseDto buildHouseDto() {
        return new HouseDto(uuidHouse, area, country, city, street, number, createDate);
    }

    public HouseRequest buildHouseRequest() {
        return new HouseRequest(area, country, city, street, number);
    }

    public HouseResponse buildHouseResponse() {
        return new HouseResponse(uuidHouse, area, country, city, street, number, createDate);
    }
}
