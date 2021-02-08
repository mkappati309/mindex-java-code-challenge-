
package com.mindex.challenge.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureImpl implements ReportingStructureService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureImpl.class);

	@Autowired
	private EmployeeService employeeService;

	@Override
	public ReportingStructure getReports(String employeeId) {
		Employee employee = employeeService.read(employeeId);
		return getReports(employee);
	}

	/*
	 * Method to return the reporting structure.
	 */

	int noOfDirectReports;

	@Override
	public ReportingStructure getReports(Employee employee) {
		noOfDirectReports = 0;
		LOG.debug("Creating employee structure for [{}]", employee);
		ReportingStructure reportingStructure = new ReportingStructure();
		if (employee.getDirectReports() != null) {
			subReporting(employee);
		}

		reportingStructure.setEmployee(employee);
		reportingStructure.setNumberOfReports(noOfDirectReports);
		LOG.debug("noOfDirectReports are " + noOfDirectReports);
		return reportingStructure;
	}

	/*
	 * This method will return back the sub reports.
	 */
	private Employee subReporting(Employee employee) {
		List<Employee> subList = new ArrayList<>();
		if (employee.getDirectReports() != null) {
			for (Employee subEmp : employee.getDirectReports()) {
				noOfDirectReports++;
				LOG.debug("Inside sub reporting employee information [{}]" + subEmp.getEmployeeId());
				subEmp = employeeService.read(subEmp.getEmployeeId());
				subList.add(subEmp);
				if (subEmp.getDirectReports() != null) {
					subReporting(subEmp);
				}
			}
			employee.setDirectReports(subList);
		}
		return employee;
	}
}
