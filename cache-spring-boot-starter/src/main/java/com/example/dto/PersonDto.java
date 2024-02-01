package com.example.dto;

import com.example.dto.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

	private UUID uuidPerson;
	private String name;
	private String surname;
	private Sex sex;
	private String passportSeries;
	private String passportNumber;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;

}
