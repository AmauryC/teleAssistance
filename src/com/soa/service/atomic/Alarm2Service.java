package com.soa.service.atomic;

import java.util.Map;

import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class Alarm2Service extends AlarmService {

	public Alarm2Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static AlarmService main(String[] args) {
		Alarm2Service alarmService = new Alarm2Service("AlarmService", "se.lnu.course4dv109.service.alarm2");

		Map<String, Object> customProperties = alarmService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", 0.04);
		customProperties.put("Performance", 0.9);
		customProperties.put("Cost", 2.5);
		
		alarmService.getServiceDescription().setResponseTime(1);
		
		alarmService.startService();
		alarmService.register();

		return alarmService;
	}

}