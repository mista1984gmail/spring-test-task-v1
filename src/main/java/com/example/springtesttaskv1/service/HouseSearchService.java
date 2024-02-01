package com.example.springtesttaskv1.service;


import com.example.springtesttaskv1.entity.dto.HouseDto;

import java.util.List;

public interface HouseSearchService {

    List<HouseDto> findByCountryOrCityOrStreet(String name);

}
