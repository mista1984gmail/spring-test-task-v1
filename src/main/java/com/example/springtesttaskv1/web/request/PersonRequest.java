package com.example.springtesttaskv1.web.request;

import com.example.springtesttaskv1.entity.enums.Sex;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@NotEmpty
	private String surname;

	@NotNull
	private Sex sex;

	@NotNull
	@Size(min = 2, max = 2, message
			= "Passport series must be 2 characters")
	private String passportSeries;

	@Size(min = 7, max = 27, message
			= "Passport series must be 7 characters")
	@NotNull
	private String passportNumber;

}
