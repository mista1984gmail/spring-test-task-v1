package com.example.springtesttaskv1.service;


import com.example.springtesttaskv1.entity.dto.HouseDto;
import com.example.springtesttaskv1.entity.dto.PersonDto;
import com.example.springtesttaskv1.web.request.ResidenceRequest;

import java.util.List;
import java.util.UUID;

public interface ResidentService {

    List<PersonDto> getResidents(UUID uuidHouse);
    HouseDto getHouseLivePerson(UUID uuidPerson);
    void changeResidence(ResidenceRequest changeResidenceRequest);

}
