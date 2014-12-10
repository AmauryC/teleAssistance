package com.soa.service.atomic;

import java.util.Map;

import com.webapp.server.WorkflowServiceImpl;

import service.auxiliary.ServiceOperation;

public class Alarm1Service extends AlarmService {

	public Alarm1Service(String serviceName, String serviceEndpoint) {
		super(serviceName, serviceEndpoint);
	}

	public static AlarmService main(String[] args) {
		AlarmService alarmService = new Alarm1Service("AlarmService", "se.lnu.course4dv109.service.alarm1");

		Map<String, Object> customProperties = alarmService.getServiceDescription().getCustomProperties();
		customProperties.put("Reliability", 0.03);
		customProperties.put("Performance", 1.1);
		customProperties.put("Cost", 4.1);
		
		alarmService.getServiceDescription().setResponseTime(2);
		
		alarmService.startService();
		alarmService.register();

		return alarmService;
	}

}