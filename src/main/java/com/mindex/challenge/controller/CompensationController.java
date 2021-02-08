package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@RestController
public class CompensationController {

	private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

	@Autowired
	private CompensationService compensationService;

	@RequestMapping(value = "/compensation", method = RequestMethod.POST)
	public Compensation createCompensation(@RequestBody Compensation compensation) {
		LOG.debug("Received compensation create request for [{}]", compensation);
		return compensationService.createCompensation(compensation);
	}

	@RequestMapping(value = "/compensation/{employeeId}", method = RequestMethod.GET)
	public Compensation read(@PathVariable String employeeId) {
		LOG.debug("Received compensation read request for [{}]", employeeId);
		return compensationService.read(employeeId);
	}
}
