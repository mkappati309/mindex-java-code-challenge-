package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeStructureServiceImpl {

	private String readReportingStructureUrl;
	private String createEmployeeUrl;

	@Autowired
	private EmployeeRepository employeeRepository;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {

		readReportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{employeeId}";
		createEmployeeUrl = "http://localhost:" + port + "/employee";
	}

	@Test
	public void test() {
		Employee emp1 = createEmployee("emp1", "John", "Chris", "Manager", "Engineering", "emp2");
		Employee emp2 = createEmployee("emp2", "Mike", "Creek", "Developer", "Development", "emp3");
		Employee emp3 = createEmployee("emp3", "Eric", "Taubman", "Developer", "Development", null);

		employeeRepository.save(emp1);
		employeeRepository.save(emp2);
		employeeRepository.save(emp3);

		ReportingStructure createdStructure = restTemplate
				.getForEntity(readReportingStructureUrl, ReportingStructure.class, "emp1").getBody();
		assertEquals(2, createdStructure.getNumberOfReports());
		assertEquals("John", createdStructure.getEmployee().getFirstName());
		assertEquals("Chris", createdStructure.getEmployee().getLastName());

	}

	private Employee createEmployee(String employeeId, String firstName, String lastName, String position,
			String department, String subReportingId) {
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setDepartment(department);
		List<Employee> empList = new ArrayList<>();
		if (subReportingId != null) {
			Employee subReportingEmployee = new Employee();
			subReportingEmployee.setEmployeeId(subReportingId);
			empList.add(subReportingEmployee);
			employee.setDirectReports(empList);
		}

		return employee;

	}
}
