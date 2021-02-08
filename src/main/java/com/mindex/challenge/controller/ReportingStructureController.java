package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@RestController
public class ReportingStructureController {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

	@Autowired
	private ReportingStructureService reportingStructureService;

	@RequestMapping(value = "/reportingStructure/{employeeId}", method = RequestMethod.GET)
	public ReportingStructure getReports(@PathVariable String employeeId) {
		LOG.debug("Received reporting structure get request for employeeId [{}]", employeeId);
		return reportingStructureService.getReports(employeeId);
	}
}
