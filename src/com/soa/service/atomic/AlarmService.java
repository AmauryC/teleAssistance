package com.soa.service.atomic;

import service.atomic.AtomicService;
import service.auxiliary.ServiceOperation;

public class AlarmService extends AtomicService {

	public AlarmService(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	@ServiceOperation
	public void triggerAlarm(int origin) {
		System.out.println("CALLING EMERGENCY ... !");
	}
}
