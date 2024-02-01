package com.example.springtesttaskv1.mapper;

import com.example.springtesttaskv1.entity.dto.HouseHistoryDto;
import com.example.springtesttaskv1.entity.model.HouseHistory;
import org.mapstruct.Mapper;

@Mapper
public interface HouseHistoryMapper {

	HouseHistory dtoToEntity(HouseHistoryDto houseHistoryDto);
}
