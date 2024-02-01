package com.example.springtesttaskv1.mapper;

import com.example.springtesttaskv1.entity.dto.PersonDto;
import com.example.springtesttaskv1.entity.model.Person;
import com.example.springtesttaskv1.web.request.PersonRequest;
import com.example.springtesttaskv1.web.response.PersonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface PersonMapper {

	@Mapping(ignore = true, target = "id")
	List<PersonDto> entityListToDtoList(List<Person> people);
	PersonDto entityToDto(Person person);
	Person requestToModel(PersonRequest personRequest);
	PersonResponse dtoToResponse(PersonDto personDto);
	List<PersonResponse> dtoListToResponseList(List<PersonDto> people);
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createDate", ignore = true)
	void mergeEntity(@MappingTarget Person target, PersonRequest source);

}
