package com.example.springtesttaskv1.service.impl;

import com.example.springtesttaskv1.entity.dto.HouseDto;
import com.example.springtesttaskv1.mapper.HouseMapper;
import com.example.springtesttaskv1.repository.HouseRepository;
import com.example.springtesttaskv1.service.HouseSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseSearchServiceImpl implements HouseSearchService {

    private final HouseRepository houseRepository;
    private final HouseMapper houseMapper;

    /**
     * Производит поиск всех домов по названию
     * страны (country), города (city) или улицы (street)
     *
     * @param name - название страны, города или улицы
     * @return лист с информацией о HouseDto
     */
    @Override
    public List<HouseDto> findByCountryOrCityOrStreet(String name) {
        log.info("Find all Houses with country or city or street equals {}", name);
        return houseMapper.entityListToDtoList(houseRepository.findByCountryOrCityOrStreet(name));
    }

}
