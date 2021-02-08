package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService {

	private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

	@Autowired
	private CompensationRepository compensationRepository;

	@Override
	public Compensation createCompensation(Compensation compensation) {
		LOG.debug("Creating compensation [{}]", compensation);
		compensationRepository.insert(compensation);
		return compensation;
	}

	@Override
	public Compensation read(String employeeId) {
		LOG.debug("Creating employee with id [{}]", employeeId);
		Compensation compensation = compensationRepository.findByEmployeeId(employeeId);
		if (compensation == null) {
			throw new RuntimeException("No compensation details found for employeeID " + employeeId);
		}
		return compensation;
	}
}
