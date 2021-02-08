package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

/*
 * Service layer for the reporting structure service. 
 */
public interface ReportingStructureService {

	/*
	 * Service layer method to implement the getReports functionality.
	 */
	ReportingStructure getReports(String employeeId);
	ReportingStructure getReports(Employee employee);
}
