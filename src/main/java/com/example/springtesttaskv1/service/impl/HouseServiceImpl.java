package com.example.springtesttaskv1.service.impl;

import com.example.aspect.annotation.DeleteObjectFromCache;
import com.example.aspect.annotation.GetObjectFromCache;
import com.example.aspect.annotation.SaveObjectToCache;
import com.example.aspect.annotation.UpdateObjectInCache;
import com.example.springtesttaskv1.entity.dto.HouseDto;
import com.example.springtesttaskv1.entity.model.House;
import com.example.springtesttaskv1.exception.EntityNotFoundException;
import com.example.springtesttaskv1.mapper.HouseMapper;
import com.example.springtesttaskv1.repository.HouseRepository;
import com.example.springtesttaskv1.service.HouseService;
import com.example.springtesttaskv1.util.Constants;
import com.example.springtesttaskv1.web.request.HouseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final HouseMapper houseMapper;

    /**
     * Создаёт новоый House из HouseRequest
     * задает рандомный UUID и время создания
     *
     * @param houseRequest HouseRequest
     */
    @Override
    @Transactional
    @SaveObjectToCache
    public HouseDto save(HouseRequest houseRequest) {
        HouseDto houseToSave = houseMapper.requestToDto(houseRequest);
        houseToSave.setUuidHouse(UUID.randomUUID());
        houseToSave.setCreateDate(LocalDateTime.now());
        log.info("House with uuid {} saved", houseToSave.getUuidHouse());
        House houseSaved = houseRepository.save(houseMapper.dtoToEntity(houseToSave));
        return houseMapper.entityToDto(houseSaved);
    }

    /**
     * Удаляет существующий House
     * Используется для уделения soft delete
     *
     * @param uuid идентификатор House для удаления
     * @throws EntityNotFoundException если House не найден
     */
    @Override
    @Transactional
    @DeleteObjectFromCache
    public void delete(UUID uuid) {
        houseRepository.delete(getByUUID(uuid));
        log.info("House with uuid {} deleted", uuid);
    }

    /**
     * Обновляет уже существующий House из информации полученной в HouseRequest
     *
     * @param uuid     идентификатор House для обновления
     * @param houseRequest HouseRequest с информацией об обновлении
     * @throws EntityNotFoundException если House не найден
     */
    @Override
    @Transactional
    @UpdateObjectInCache
    public HouseDto update(UUID uuid, HouseRequest houseRequest) {
        House houseFromDB = getByUUID(uuid);
        houseMapper.mergeEntity(houseFromDB, houseRequest);
        log.info("House with uuid {} updated", uuid);
        return houseMapper.entityToDto(houseRepository.save(houseFromDB));
    }

    /**
     * Ищет House по идентификатору
     *
     * @param uuid идентификатор House
     * @return найденный HouseDto
     * @throws EntityNotFoundException если House не найден
     */
    @Override
    @GetObjectFromCache
    public HouseDto findByUUID(UUID uuid) {
        log.info("House with uuid {} found", uuid);
        return Optional.of(getByUUID(uuid))
                .map(houseMapper::entityToDto)
                .get();
    }

    /**
     * Возвращает все существующие House
     *
     * @param page номер страницы
     * @param size количество House на странице (по умолчанию 15)
     * @param orderBy по какому полю сортировать (по умолчанию "city")
     * @param direction как сотрировать (по умолчению "ASC")
     * @return лист с информацией о HouseDto
     */
    @Override
    public Page<HouseDto> findAllWithPaginationAndSorting(Integer page, Integer size, String orderBy, String direction) {
        log.info("Show Houses on the page {}", page );
        if (orderBy == null || orderBy.isEmpty()) {
            orderBy = Constants.DEFAULT_HOUSE_ORDER_BY;
        }
        if (direction == null || direction.isEmpty()) {
            direction = Constants.DEFAULT_DIRECTION;
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<House> foundHouses = houseRepository.findAll(pageRequest);
        return foundHouses.map(houseMapper::entityToDto);
    }

    /**
     * Ищет House по идентификатору
     *
     * @param uuid идентификатор House
     * @return найденный House
     * @throws EntityNotFoundException если House не найден
     */
    private House getByUUID(UUID uuid) {
        return houseRepository.findByUuidHouse(uuid)
                .orElseThrow(() -> new EntityNotFoundException(House.class, uuid));
    }

}
