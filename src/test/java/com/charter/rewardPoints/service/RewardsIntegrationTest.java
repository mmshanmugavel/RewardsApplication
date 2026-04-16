package com.charter.rewardpoints.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardsIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testCalculateRewards_Integration() {
		String url = "/api/rewards/calculate?fromDate=" + LocalDate.now().minusMonths(3) + "&toDate=" + LocalDate.now();
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
	}
}
