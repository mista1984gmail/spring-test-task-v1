package com.example.springtesttaskv1.service.impl;

import com.example.springtesttaskv1.entity.dto.HouseDto;
import com.example.springtesttaskv1.entity.model.House;
import com.example.springtesttaskv1.exception.EntityNotFoundException;
import com.example.springtesttaskv1.mapper.HouseMapper;
import com.example.springtesttaskv1.repository.HouseRepository;
import com.example.springtesttaskv1.util.ConstantsForTest;
import com.example.springtesttaskv1.util.HouseTestData;
import com.example.springtesttaskv1.web.request.HouseRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HouseServiceImplTest {
    @Mock
    private HouseRepository houseRepository;

    @Mock
    private HouseMapper houseMapper;

    @InjectMocks
    private HouseServiceImpl houseService;

    @Test
    void shouldDeleteHouse(){
        // given
        UUID uuid = ConstantsForTest.UUID_HOUSE;
        Optional<House> house = Optional.of(HouseTestData.builder()
                .build()
                .buildHouse());

        when(houseRepository.findByUuidHouse(uuid))
                .thenReturn(house);
        doNothing()
                .when(houseRepository)
                .delete(house.get());

        //when
        houseService.delete(uuid);

        //then
        verify(houseRepository).delete(any());
    }
    @Test
    void shouldNotDeleteHouseAndThrowsHouseNotFoundException() {
        // given
        UUID uuid = ConstantsForTest.UUID_HOUSE;
        String errorMessage = "House with uuid: " + uuid + " not found";
        Optional<House> house = Optional.of(HouseTestData.builder()
                .withId(null)
                .withUuidHouse(null)
                .build()
                .buildHouse());

        when(houseRepository.findByUuidHouse(uuid))
                .thenThrow(new EntityNotFoundException(House.class, uuid));

        //when
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            houseService.delete(uuid);
        });

        //then
        assertEquals(errorMessage, thrown.getMessage());
        verify(houseRepository, never()).delete(house.get());
    }

    @Test
    void shouldNotGetHouseByUUIDAndThrowsHouseNotFoundException() {
        // given
        UUID uuid = ConstantsForTest.UUID_HOUSE;
        String errorMessage = "House with uuid: " + uuid + " not found";

        when(houseRepository.findByUuidHouse(uuid))
                .thenThrow(new EntityNotFoundException (House.class, uuid));

        //when
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            houseService.findByUUID(uuid);
        });

        //then
        assertEquals(errorMessage, thrown.getMessage());
    }

   @Test
    void shouldGetHouseByUUID() {
        // given
        UUID uuid = ConstantsForTest.UUID_HOUSE;
        Optional<House> house = Optional.of(HouseTestData.builder()
                .build()
                .buildHouse());
        HouseDto expected = HouseTestData.builder()
                .build()
                .buildHouseDto();

        when(houseRepository.findByUuidHouse(uuid))
                .thenReturn(house);
        when(houseMapper.entityToDto(house.get()))
                .thenReturn(expected);

        //when
        HouseDto actual = houseService.findByUUID(uuid);

        //then
        assertEquals(expected, actual);
        verify(houseRepository).findByUuidHouse(uuid);
        verify(houseMapper).entityToDto(house.get());
    }

     @Test
    void shouldCreateHouse() {
        // given
        HouseRequest houseRequestForSave = HouseTestData.builder()
                .build()
                .buildHouseRequest();
         HouseDto houseForSave = HouseTestData.builder()
                .build()
                .buildHouseDto();
        HouseDto expected = HouseTestData.builder()
                .build()
                .buildHouseDto();
         House house = HouseTestData.builder()
                 .build()
                 .buildHouse();

        when(houseMapper.requestToDto(houseRequestForSave))
                .thenReturn(houseForSave);
        when(houseMapper.dtoToEntity(houseForSave))
                 .thenReturn(house);
        when(houseRepository.save(house))
                .thenReturn(house);
         when(houseMapper.entityToDto(house))
                 .thenReturn(expected);

        //when
        HouseDto actual = houseService.save(houseRequestForSave);

        //then
        verify(houseRepository).save(house);

        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdateHouse() {
        // given
       HouseDto houseDtoForUpdate = HouseTestData.builder()
                .withCity("Grodno")
                .withStreet("Pushkina")
                .withNumber(123)
                .build()
                .buildHouseDto();
        HouseDto houseDtoSaved = HouseTestData.builder()
                .withCity("Grodno")
                .withStreet("Pushkina")
                .withNumber(123)
                .build()
                .buildHouseDto();
        Optional<House> optionalHouseFromDB = Optional.of(HouseTestData.builder()
                .build()
                .buildHouse());
        HouseRequest houseRequestForUpdate = HouseTestData.builder()
                .withCity("Grodno")
                .withStreet("Pushkina")
                .withNumber(123)
                .build()
                .buildHouseRequest();
        House houseFromDB = HouseTestData.builder()
                .withCity("Grodno")
                .withStreet("Pushkina")
                .withNumber(123)
                .build()
                .buildHouse();
        UUID uuid = ConstantsForTest.UUID_HOUSE;

        when(houseRepository.findByUuidHouse(uuid)).thenReturn(optionalHouseFromDB);
        when(houseRepository.save(any()))
                .thenReturn(houseFromDB);
        when(houseMapper.entityToDto(houseFromDB))
                .thenReturn(houseDtoSaved);

        //when
        HouseDto expected = houseService.update(uuid, houseRequestForUpdate);

        //then
        verify(houseRepository).save(any());

        assertThat(houseDtoForUpdate)
                .hasFieldOrPropertyWithValue(House.Fields.uuidHouse, expected.getUuidHouse())
                .hasFieldOrPropertyWithValue(House.Fields.area, expected.getArea())
                .hasFieldOrPropertyWithValue(House.Fields.country, expected.getCountry())
                .hasFieldOrPropertyWithValue(House.Fields.city, expected.getCity())
                .hasFieldOrPropertyWithValue(House.Fields.street, expected.getStreet())
                .hasFieldOrPropertyWithValue(House.Fields.number, expected.getNumber())
                .hasFieldOrPropertyWithValue(House.Fields.createDate, expected.getCreateDate());
    }

   @Test
    void shouldNotUpdateHouseAndThrowsHouseNotFoundException() {
        // given
        UUID uuid = ConstantsForTest.UUID_HOUSE;
        HouseRequest houseRequestForUpdate = HouseTestData.builder()
                .withCity("Grodno")
                .withStreet("Pushkina")
                .withNumber(123)
                .build()
                .buildHouseRequest();
        String errorMessage = "House with uuid: " + uuid + " not found";

       when(houseRepository.findByUuidHouse(uuid))
               .thenThrow(new EntityNotFoundException (House.class, uuid));

        //when
       EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
           houseService.update(uuid, houseRequestForUpdate);
       });

        //then
        assertEquals(errorMessage, thrown.getMessage());
    }

}