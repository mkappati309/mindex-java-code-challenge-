package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

	private String createCompensationUrl;
	private String readCompensationUrl;

	@Autowired
	private CompensationService compensationService;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		createCompensationUrl = "http://localhost:" + port + "/compensation";
		readCompensationUrl = "http://localhost:" + port + "/compensation/{employeeId}";
	}

	@Test
	public void testCreateRead() {
		Compensation testCompensation = createCompensation("16a596ae-edd3-4847-99fe-c4518e82c86f", 10000);

		// Create checks
		Compensation createdCompensation = restTemplate
				.postForEntity(createCompensationUrl, testCompensation, Compensation.class).getBody();

		assertNotNull(createdCompensation.getEmployeeId());
		assertEmployeeEquivalence(testCompensation, createdCompensation);

		// Read checks
		Compensation readCompensation = restTemplate
				.getForEntity(readCompensationUrl, Compensation.class, createdCompensation.getEmployeeId()).getBody();
		assertEquals(createdCompensation.getEmployeeId(), readCompensation.getEmployeeId());
		assertEmployeeEquivalence(createdCompensation, testCompensation);
	}

	private void assertEmployeeEquivalence(Compensation expected, Compensation actual) {
		assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
		assertEquals(expected.getSalary(), actual.getSalary());
		assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
	}
	
	private Compensation createCompensation(String employeeId, int salary)	{
		Compensation testCompensation = new Compensation();
		testCompensation.setEmployeeId(employeeId);
		testCompensation.setSalary(salary);
		testCompensation.setEffectiveDate(new Date());
		return testCompensation;
	}
}
