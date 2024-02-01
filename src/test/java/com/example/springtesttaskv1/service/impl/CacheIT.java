package com.example.springtesttaskv1.service.impl;

import com.example.springtesttaskv1.entity.dto.HouseDto;
import com.example.springtesttaskv1.mapper.HouseMapper;
import com.example.springtesttaskv1.repository.HouseRepository;
import com.example.springtesttaskv1.util.HouseTestData;
import com.example.springtesttaskv1.web.request.HouseRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@Testcontainers
@SpringBootTest(properties = {"spring.main.allow-bean-definition-overriding=true",
		"spring.mvc.pathmatch.matching-strategy = ANT_PATH_MATCHER"})
public class CacheIT {

	@Container
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12")
			.withUsername("username")
			.withPassword("password")
			.withExposedPorts(5432)
			.withReuse(true);

	@DynamicPropertySource
	static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
		registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
	}

	@Spy
	private HouseRepository houseRepository;

	@Autowired
	private HouseMapper houseMapper;

	@Autowired
	private HouseServiceImpl houseService;

	@Test
	void shouldGetHouseFromCache_whenSaveHouseAndTwoTimesGetIt() {
		//given
		HouseRequest houseRequestForSave = HouseTestData.builder()
														.build()
														.buildHouseRequest();
		HouseDto houseSaved = houseService.save(houseRequestForSave);
		UUID uuid = houseSaved.getUuidHouse();
		int numberOfThreads = 2;
		ExecutorService service = Executors.newFixedThreadPool(6);

		//when
		for (int i = 0; i < numberOfThreads; i++) {
			service.execute(() -> {
				houseService.findByUUID(uuid);
			});
		}
		service.shutdown();

		//then
		verify(houseRepository, never()).findByUuidHouse(uuid);
	}

}
