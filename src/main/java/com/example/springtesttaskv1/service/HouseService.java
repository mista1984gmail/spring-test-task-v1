package com.example.springtesttaskv1.service;


import com.example.springtesttaskv1.entity.dto.HouseDto;
import com.example.springtesttaskv1.web.request.HouseRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface HouseService {

    HouseDto save(HouseRequest houseRequest);

    void delete(UUID uuid);

    HouseDto update(UUID uuid, HouseRequest houseRequest);

    HouseDto findByUUID(UUID uuid);

    Page<HouseDto> findAllWithPaginationAndSorting(Integer page, Integer size, String orderBy, String direction);

}
