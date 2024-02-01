package com.example.springtesttaskv1.service;


import com.example.springtesttaskv1.entity.dto.HouseDto;
import com.example.springtesttaskv1.entity.dto.PersonDto;
import com.example.springtesttaskv1.web.request.OwnerRequest;

import java.util.List;
import java.util.UUID;

public interface OwnerService {

    List<PersonDto> findAllOwners(UUID uuidHouse);
    List<HouseDto> findAllHousesPerson(UUID uuidPerson);

    void addOwner(OwnerRequest ownerRequest);

    void removeOwner(OwnerRequest ownerRequest);

}
