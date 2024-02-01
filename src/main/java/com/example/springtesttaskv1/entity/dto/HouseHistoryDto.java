package com.example.springtesttaskv1.entity.dto;

import com.example.springtesttaskv1.entity.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseHistoryDto {

	private Long houseId;
	private Long personId;
	private PersonType personType;

}