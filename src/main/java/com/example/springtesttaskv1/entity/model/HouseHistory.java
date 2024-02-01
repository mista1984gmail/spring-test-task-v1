package com.example.springtesttaskv1.entity.model;

import com.example.springtesttaskv1.entity.enums.PersonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "house_history")
public class HouseHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "house_id", updatable = false)
	private Long houseId;

	@Column(name = "person_id", updatable = false)
	private Long personId;

	@Column(name = "create_date", updatable = false)
	private LocalDateTime createDate;

	@Column(name="person_type",
			nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PersonType personType;

}