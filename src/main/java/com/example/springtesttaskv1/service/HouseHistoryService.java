package com.example.springtesttaskv1.service;


import com.example.springtesttaskv1.entity.dto.HouseDto;
import com.example.springtesttaskv1.entity.dto.HouseHistoryDto;
import com.example.springtesttaskv1.entity.dto.PersonDto;
import com.example.springtesttaskv1.entity.model.HouseHistory;

import java.util.List;
import java.util.UUID;

public interface HouseHistoryService {

	HouseHistory save(HouseHistoryDto houseHistory);

	List<PersonDto> findAllTenantsEverLivedInHouse(UUID uuidHouse);

	List<PersonDto> findAllOwnersEverOwnedHouse(UUID uuidHouse);

	List<HouseDto> findAllHousesEverLivedPerson(UUID uuidPerson);

	List<HouseDto> findAllHousesEverOwnedPerson(UUID uuidPerson);

}
