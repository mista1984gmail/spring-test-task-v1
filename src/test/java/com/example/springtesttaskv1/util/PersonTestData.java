package com.example.springtesttaskv1.util;

import com.example.springtesttaskv1.entity.dto.PersonDto;
import com.example.springtesttaskv1.entity.enums.Sex;
import com.example.springtesttaskv1.entity.model.Person;
import com.example.springtesttaskv1.web.request.PersonRequest;
import com.example.springtesttaskv1.web.response.PersonResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class PersonTestData {

    @Builder.Default
    private Long id = ConstantsForTest.ID;

    @Builder.Default
    private UUID uuidPerson = ConstantsForTest.UUID_PERSON;

    @Builder.Default
    private String name = ConstantsForTest.NAME;

    @Builder.Default
    private String surname = ConstantsForTest.SURNAME;

    @Builder.Default
    private Sex sex = ConstantsForTest.SEX;

    @Builder.Default
    private String passportSeries = ConstantsForTest.PASSPORT_SERIES;

    @Builder.Default
    private String passportNumber = ConstantsForTest.PASSPORT_NUMBER;

    @Builder.Default
    private LocalDateTime createDate = ConstantsForTest.CREATE_DATE;

    @Builder.Default
    private LocalDateTime updateDate = ConstantsForTest.UPDATE_DATE;

    @Builder.Default
    private Long liveHouseId = ConstantsForTest.LIVE_HOUSE_ID;

    public Person buildPerson() {
        return new Person(id, uuidPerson, name, surname, sex, passportSeries, passportNumber, createDate, updateDate);
    }

    public PersonDto buildPersonDto() {
        return new PersonDto(uuidPerson, name, surname, sex, passportSeries, passportNumber, createDate, updateDate);
    }

    public PersonRequest buildPersonRequest() {
        return new PersonRequest(name, surname, sex, passportSeries, passportNumber);
    }

    public PersonResponse buildPersonResponse() {
        return new PersonResponse(uuidPerson, name, surname, sex, passportSeries, passportNumber, createDate, updateDate);
    }
}
