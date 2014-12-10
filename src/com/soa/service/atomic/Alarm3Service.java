package com.soa.service.atomic;

import java.util.Map;

import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class Alarm3Service extends AlarmService {

	public Alarm3Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static AlarmService main(String[] args) {
		Alarm3Service alarmService = new Alarm3Service("AlarmService", "se.lnu.course4dv109.service.alarm3");

		Map<String, Object> customProperties = alarmService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", 0.008);
		customProperties.put("Performance", 0.3);
		customProperties.put("Cost", 6.8);
		
		alarmService.getServiceDescription().setResponseTime(1);
		
		alarmService.startService();
		alarmService.register();

		return alarmService;
	}

}